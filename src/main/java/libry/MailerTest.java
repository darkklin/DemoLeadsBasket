package libry;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;

import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.AndTerm;
import javax.mail.search.FlagTerm;
import javax.mail.search.SearchTerm;


import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MailerTest {

	public static String  checkMail(String username, String password,String fromAdress) {
		Object body = null;

		Properties props = new Properties();
		props.setProperty("mail.store.protocol", "imaps");
		try {
			Session session = Session.getInstance(props, null);
			Store store = session.getStore();
			store.connect("imap.gmail.com", username, password);
			Folder inbox = store.getFolder("INBOX");
			inbox.open(Folder.READ_WRITE);

			// looking for email UNread and the new one
			SearchTerm searchTerm = new AndTerm(new FlagTerm(new Flags(Flags.Flag.SEEN), false),
					new FlagTerm(new Flags(Flags.Flag.RECENT), false));

			Message msgs[] = inbox.search(searchTerm);

			System.out.println("No. of Unread Messages : " + msgs.length);
			for (Message msg : msgs) {
				try {
					Address[] in = msg.getFrom();
					String from = null;
					for (Address address : in) {

//						System.out.println("FROM:" + address.toString());
						from = address.toString();
						
					}
					if(from.contains(fromAdress))
					{

					 body = msg.getContent();
					if (body instanceof MimeMultipart) {
						MimeMultipart multipart = (MimeMultipart) body;
						if (multipart.getCount() > 0) {
							BodyPart part = multipart.getBodyPart(0);
							String content = part.getContent().toString();
							if (content.contains("javax.mail")) {
								MimeMultipart mp = (MimeMultipart) msg.getContent();
								body = getTextFromMimeMultipart(mp);

							}
						}
					}
//					System.out.println("SENT DATE:" + msg.getSentDate());
//					System.out.println("SUBJECT:" + msg.getSubject());
//					System.out.println("CONTENT:" + body);
					// System.out.println(extractUrls(body));
					msg.setFlag(Flags.Flag.SEEN, true);
					}
				} catch (Exception e) {

					e.printStackTrace();
				}
			}	
			// close folder and store (normally in a finally block)
			inbox.close(false);
			store.close();

		} catch (Exception mex) {
			mex.printStackTrace();
		}
		return (String) body;
	}
	public static List<String> extractUrls(String text) {
		List<String> containedUrls = new ArrayList<String>();
		String urlRegex = "((https?|ftp|gopher|telnet|file):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
		Pattern pattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE);
		Matcher urlMatcher = pattern.matcher(text);

		while (urlMatcher.find()) {
			containedUrls.add(text.substring(urlMatcher.start(0), urlMatcher.end(0)));
		}

		return containedUrls;
	}
	private static String getTextFromMimeMultipart(MimeMultipart mimeMultipart) throws Exception {
		String result = "";
		int partCount = mimeMultipart.getCount();
		for (int i = 0; i < partCount; i++) {
			BodyPart bodyPart = mimeMultipart.getBodyPart(i);
			if (bodyPart.isMimeType("text/plain")) {
				result = result + "\n" + bodyPart.getContent();
				break; // without break same text appears twice in my tests
			} else if (bodyPart.isMimeType("text/html")) {
				String html = (String) bodyPart.getContent();
				// result = result + "\n" + org.jsoup.Jsoup.parse(html).text();
				result = html;
			} else if (bodyPart.getContent() instanceof MimeMultipart) {
				result = result + getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent());
			}
		}
		return result;
	}

}


