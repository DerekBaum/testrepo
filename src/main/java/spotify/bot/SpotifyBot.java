package spotify.bot;

import java.util.List;

import javax.security.auth.login.LoginException;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import com.sedmelluq.lavaplayer.loopback.LoopbackAudioSourceManager;
import com.sedmelluq.lavaplayer.loopback.LoopbackAudioTrack;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import net.dv8tion.jda.core.managers.AudioManager;
import net.dv8tion.jda.core.managers.GuildController;

public class SpotifyBot {
	//compile 'net.dv8tion:JDA:3.0.0_193'
	
	private final static String derektoken = "MzEyNjg4ODU1MjYwMzMyMDUz.C_fJlQ._KfZiwJ4x3pEW0tbQLFpWGksFQ8";
	private final static String isaactoken = "MzEzNDIzNjYzMTcwODQ2NzIw.C_paGA.ttEyp2y5lPPjHspxJATcq5pttJg";
	private final static String jaredtoken = "MzE2Njc4Mjc1MjU2NTQ5Mzg3.DAYxJQ.oGKzSJHq9Nq2wwizOJShj8i9B8I";
	private final static String channelID = "339592343457890308";
	private final static String derekOWNER_ID = "154788701266771968";
	private final static String isaacOWNER_ID = "185585041059872768";
	private final static String jaredOWNER_ID = "112427446434869248";
	private final static String GUILD_ID = "299749316753620992";
	public static void main(String[] args) throws LoginException, IllegalArgumentException, RateLimitedException, InterruptedException{
		
		JDA api = new JDABuilder(AccountType.BOT).setToken(jaredtoken).buildAsync();
		Thread.sleep(1000);
		api.addEventListener(new MyListener(api.getUserById(jaredOWNER_ID),api.getGuildById(GUILD_ID)));
		System.out.println(api.getVoiceChannelById(channelID));
		VoiceChannel vc = api.getVoiceChannelById(channelID);
		connectTo(vc);
		
		
		GuildController gc = new GuildController(api.getGuildById(GUILD_ID));
		
		
		
		AudioPlayerManager playerManager = new DefaultAudioPlayerManager();
		AudioSourceManagers.registerRemoteSources(playerManager);
		
		LoopbackAudioSourceManager loopbackSource = new LoopbackAudioSourceManager();
//		LoopbackAudioTrack loopbackTrack = new LoopbackAudioTrack(loopbackSource, "Port CABLE Input (VB-Audio Virtual Cable)"); //isaac
		LoopbackAudioTrack loopbackTrack = new LoopbackAudioTrack(loopbackSource, "CABLE Input (VB-Audio Virtual Cable)"); //jared
//		LoopbackAudioTrack loopbackTrack = new LoopbackAudioTrack(loopbackSource, "Hi-Fi Cable Input (VB-Audio Hi-Fi Cable)"); //derek

		playerManager.registerSourceManager(loopbackSource);	
		AudioPlayer player = playerManager.createPlayer();
		
		AudioManager manager = vc.getGuild().getAudioManager();
		AudioPlayerSendHandler as = new AudioPlayerSendHandler(player);
		
		manager.setSendingHandler(as);
		manager.openAudioConnection(vc);
		
		
		player.playTrack(loopbackTrack);
		
	}
	
	public static void connectTo(VoiceChannel channel){
		AudioManager manager = channel.getGuild().getAudioManager();
		manager.openAudioConnection(channel);
	}

}
