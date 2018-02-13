package spotify.bot;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.List;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.MessageHistory;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.managers.GuildController;

public class MyListener extends ListenerAdapter {
	private final String derekOWNER_ID = "154788701266771968";
	private final String isaacOWNER_ID = "185585041059872768";
	private final String jaredOWNER_ID = "112427446434869248";
	private User myUser;
	private Guild myGuild;
	
	public MyListener(User u, Guild g){
		super();
		myUser = u;
		myGuild = g;
	}
	
	public void changeNickNames(String s){
		s = s.substring(s.indexOf("{")+1,s.indexOf("}"));
		String[] names = s.split(",");
		
		GuildController gc = new GuildController(myGuild);
		List<Member> members = myGuild.getMembers();
		
		for(Member m : members){
			if(!(m.getUser().getId().equals("185585041059872768") || m.getUser().getId().equals("312688855260332053") || m.getUser().getId().equals("313423663170846720"))){
				gc.setNickname(m,getRandomString(names)).queue();
			}
		}
		
	}
	
	public String getRandomString(String[] nameList){
		int size = nameList.length;
		if(size == 0)
			return "ERROR";

		return nameList[(int)(Math.random()*size)];
	}
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		if (event.getAuthor().isBot())
			return;
		// We don't want to respond to other bot accounts, including ourself
		Message message = event.getMessage();
		String content = message.getContentRaw();
		// getRawContent() is an atomic getter
		// getContent() is a lazy getter which modifies the content for e.g.
		// console view (strip discord formatting)
		if (content.equals("!skip")) {
			MessageChannel channel = event.getChannel();
			channel.sendMessage("Skipping Current Song (This doesn't currently work) (Sorry!)").queue(); // Important to call .queue()
													// on the RestAction
													// returned by											// sendMessage(...)
		}else if(content.equals("!exposed")){
			MessageChannel channel = event.getChannel();
			MessageHistory mh = channel.getHistory();
			mh.retrievePast(100).queue(allMessages -> { System.out.println(allMessages.size());
			if(allMessages.size() > 0){
				int x = (int)(Math.random()*allMessages.size());
				Message exposedMessage = allMessages.get(x);
				channel.sendMessage(exposedMessage.getAuthor().getName() + ": " + exposedMessage.getContentRaw()).queue();
				}});
		}else if(content.equals("!stop")){
			if(message.getAuthor().getId().equals(isaacOWNER_ID)){
				System.exit(0);
			}
		}else if(content.contains("!request")){

			System.out.println(myUser.getName());
			
			myUser.openPrivateChannel().queue(privateChannel -> {
				privateChannel.sendMessage(content.substring(content.indexOf("!request")+8)).queue();
			});	
		
			
		}else if(content.equals("!summon")){
			if(message.getAuthor().getId().equals(jaredOWNER_ID)){
				VoiceChannel vc = message.getMember().getVoiceState().getChannel();
				SpotifyBot.connectTo(vc);
			}
		}else if(content.contains("!nickChange")){
			if(message.getAuthor().getId().equals(jaredOWNER_ID))
				changeNickNames(content);
		}else if(content.contains("!aylmao") && false){
			if(message.getAuthor().getId().equals(isaacOWNER_ID) && false){
				try {
					Robot robot = new Robot();
					robot.keyPress(KeyEvent.VK_D);
					robot.keyRelease(KeyEvent.VK_D);
			    
					robot.keyPress(KeyEvent.VK_F);
					robot.keyRelease(KeyEvent.VK_F);
			    
					robot.keyPress(KeyEvent.VK_Q);
					robot.keyRelease(KeyEvent.VK_Q);
			    
					robot.keyPress(KeyEvent.VK_W);
					robot.keyRelease(KeyEvent.VK_W);
			    
					robot.keyPress(KeyEvent.VK_E);
					robot.keyRelease(KeyEvent.VK_E);
			    
					robot.keyPress(KeyEvent.VK_R);
					robot.keyRelease(KeyEvent.VK_R);
			    
					robot.keyPress(KeyEvent.VK_1);
					robot.keyRelease(KeyEvent.VK_1);
			    
					robot.keyPress(KeyEvent.VK_2);
					robot.keyRelease(KeyEvent.VK_2);
			    
					robot.keyPress(KeyEvent.VK_3);
					robot.keyRelease(KeyEvent.VK_3);
			    
					robot.keyPress(KeyEvent.VK_4);
					robot.keyRelease(KeyEvent.VK_4);
			    
					robot.keyPress(KeyEvent.VK_5);
					robot.keyRelease(KeyEvent.VK_5);
			    
					robot.keyPress(KeyEvent.VK_6);
			    	robot.keyRelease(KeyEvent.VK_6);
			    
				}catch (AWTException ex) {}
			}
		}
	}
}