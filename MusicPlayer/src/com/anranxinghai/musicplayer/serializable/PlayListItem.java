package com.anranxinghai.musicplayer.serializable;

import java.io.File;
import java.io.Serializable;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import android.util.Config;
import android.util.Log;

public class PlayListItem implements Serializable {

	private static final long serialVersionUID = 20140723L;
	private static Logger logger = Logger.getLogger(PlayListItem.class
			.getName());
	private String name = "";
	protected String displayName = "";
	protected String location = "";
	protected boolean isFile = true;
	protected long seconds = -1;
	protected boolean isSelected = false;
	protected TagInfo tagInfo = null;

	private String bitRate;
	private String sampled;
	private String channels;
	private String artist;
	private String title;
	private String comment;
	private String album;
	private String year;
	private String track;
	private String genre;
	private boolean isRead;// 是否读过标签了,免得每次都去读

	private File lyricFile;// 这个项目所关联的歌词文件
	protected static ExecutorService es = Executors.newSingleThreadExecutor();
	private int offset;// 存在这里的歌曲偏移量，以保存下来，但是又不需要去写LRC文件

	/**
	 * Contructor for playlist item.
	 * 
	 * @param name
	 *            Song name to be displayed
	 * @param location
	 *            File or URL
	 * @param seconds
	 *            Time length
	 * @param isFile
	 *            true for File instance
	 */
	public PlayListItem(String name, String location, long seconds,
			boolean isFile) {
		Log.v("PlayListItem", "PlayListItem");
		this.name = name;
		this.seconds = seconds;
		this.isFile = isFile;
		this.location = location;
	}

	protected PlayListItem() {

	}

	/**
	 * 是否初始化过了，如果没有初始化过的话，则 用它来搜歌词是会出问题的
	 * 
	 * @return isRead
	 */
	public boolean isInited() {
		return isRead;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getTrack() {
		return track;
	}

	public void setTrack(String track) {
		this.track = track;
	}

	public File getLyricFile() {
		System.out.println("lyricFile=" + lyricFile);
		return lyricFile;
	}

	public void setLyricFile(File lyricFile) {
		this.lyricFile = lyricFile;
	}

	public boolean isValid() {
		TagInfo tag = getTagInfo();
		return tag != null;
	}

	public void setDuration(long seconds) {
		this.seconds = seconds;
	}

	public void setBitRate(String bitRate) {
		this.bitRate = bitRate;
	}

	public void setSampled(String sampled) {
		this.sampled = sampled;
	}

	/**
	 * 刷新一下,并不代表文件底层就变了, 这个方法在文件播放的时候改名很方便
	 */
	// public void refresh() {
	// displayName = getFormattedDisplayName();
	// }
	
	public int getBitrate() {
		if (tagInfo != null) {
			return tagInfo.getBitRate();
		}else {
			return -1;
		}
	}

	public String getBitRate() {
		if (bitRate == null) {
			int bit = getBitrate();
			if (bit <= 0) {
				// bitRate = Config.getResource("songinfo.unknown.bitrate");
			} else {
				bit = Math.round(bit / 1000);
				if (bit > 999) {
					bit /= 100;
					bitRate = bit + "Hkbps";
				} else {
					bitRate = String.valueOf(bit) + "kbps";
				}
			}
		}
		return bitRate;
	}
	
	public int getSamplerate() {
		if (tagInfo != null) {
			return tagInfo.getSamplingRate();
		}else {
			return -1;
		}
	}

	public String  getSampled(){
		if (sampled == null) {
			int sample = getSamplerate();
			if (sample <= 0) {
				// sampled = Config.getResource("songinfo.unknown.samplerate");
			}else {
				sampled = String.valueOf(Math.round(sample/100)) + "kHz";
			}
		}
		return sampled;
	}
	
	/**
	 * Returns item name such as (hh:mm:ss) Title - Artist if available.
	 * 
	 * @return name
	 */
	public String getFormattedName(){
		return name;
	}
	
	public String getName(){
		// return Util.getSongName(location);
		return name;
	}
	
	public String getLocation(){
		return location;
	}

	/**
	 * Returns true if item to play is coming for a file.
	 * 
	 * @return
	 */
	public boolean isFile() {
		return isFile;
	}

	/**
	 * Set File flag for playslit item.
	 * 
	 * @param isFile
	 */
	public void setFile(boolean isFile) {
		this.isFile = isFile;
	}
	
	/**
	 * Returns playtime in seconds. If tag info is available then its playtime
	 * will be returned.
	 * 
	 * @return playtime
	 */
	public long getLength(){
		if ((tagInfo != null) && tagInfo.getPlayTime() > 0) {
			return tagInfo.getPlayTime();
		}else {
			return seconds;
		}
	}
	
	public void setAlbum(String album){
		this.album = album;
	}
	
	public String getAlbum(){
		if (album != null) {
			return album;
		}else if ((tagInfo = getTagInfo()) != null) {
			album = tagInfo.getAlbum();
			return album;
		}else {
			return null;
		}
	}
	
	//不明白为什么这样
	public String getChannelInfo(){
		if (channels == null) {
		}
		return channels;
	}

	public void setChannels(String channels){
		this.channels = channels;
	}
	
	public int getChannels(){
		if (tagInfo != null) {
			return tagInfo.getChannels();
		}else {
			return -1;
		}
	}
	
	public String getComment(){
		if (comment != null) {
			return comment;
		}else if ((tagInfo = getTagInfo()) != null) {
			Vector vector = tagInfo.getComment();
			if (vector != null) {
				comment = String.valueOf(vector.get(0));
				return comment;
			}
			return "";
		}else {
			return "";
		}
	}
	
	public void setComment(String commment) {
		this.comment = commment;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public String getYear() {
		if (year != null) {
			return year;
		} else if ((tagInfo = getTagInfo()) != null) {
			return year = tagInfo.getYear();
		} else {
			return "";
		}
	}

	public void setYear(String year) {
		this.year = year;
	}
	
	/**
	 * Reads file comments/tags.
	 * 
	 * @param l
	 */
	public void setLocation(String location){
		this.location = location;
	}
	
	/**
	 * Reads (or not) file comments/tags.
	 * 
	 * @param l
	 *            input location
	 * @param readInfo
	 */
/*	private void setLocation(final String l, final boolean readInfo) {
		if (isRead) {
			return;
		}
		// 如果是文件,则当场读出来
		if (isFile) {
			es.execute(new Runnable() {

				public void run() {
					setLocation0(l, readInfo);
				}
			});
		} else {// 如果不是文件,则起个线程异步读出来
			new Thread() {

				public void run() {
					setLocation0(l, readInfo);
				}
			}.start();
		}

	}*/

	/**
	 * Return TagInfo.
	 * 
	 * @return tagInfo
	 */
	public TagInfo getTagInfo() {
		/*if (tagInfo == null) {
			if (Config.getConfig().getReadTagInfoStrategy()
					.equals(Config.READ_WHEN_PLAY)) {
				if (Config.getConfig().getPlayer().getCurrentItem() == this) {
					setLocation(location, true);
				}
			} else {
				setLocation(location, true);
			}
		}*/
		return tagInfo;
	}
	
	public String getFormat(){
		String format = location.substring(location.indexOf(".") + 1);
		format += " " + getSampled() + " " + getBitRate();
		return format;
	}	
	
	public String getType(){

		if ((tagInfo = getTagInfo()) != null) {
			return tagInfo.getType();
		}else {
			return null;
		}
	}
	
	public String getTitle(){
		if ((tagInfo = getTagInfo()) != null) {
			title = tagInfo.getTitle() == null ? name:tagInfo.getTitle().trim();
		}else if (title != null) {
			return title;
		}else {
			title = name;
		}
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getArtist(){
		if ((tagInfo = getTagInfo()) != null) {
			artist = tagInfo.getArtist() == null ? "" : tagInfo.getArtist().trim(); 
		} else if (artist != null) {
			return artist;
		}
		return artist;
	}
	
	public void setArtist(String artist) {
		this.artist = artist;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		//tagInfo = getTagInfo();
		return displayName;
	}
}
