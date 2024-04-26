package com.vapeur.beans;

import java.io.Serializable;

public class Language implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int id;
    private String localeCode;
    private String language;
    private boolean interfaceSupport;
    private boolean fullAudioSupport;
    private boolean subtitles;

    public Language() {
    }

   
    public Language(int id, String localeCode, String language, boolean interfaceSupport, boolean fullAudioSupport, boolean subtitles) {
		super();
		setId(id);
		setLocaleCode(localeCode);
		setLanguage(language);
		setInterfaceSupport(interfaceSupport);
		setFullAudioSupport(fullAudioSupport);
		setSubtitles(subtitles);
	}

	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocaleCode() {
        return localeCode;
    }

    public void setLocaleCode(String localeCode) {
        this.localeCode = localeCode;
    }

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}	

	public boolean isInterfaceSupport() {
		return interfaceSupport;
	}

	public void setInterfaceSupport(boolean interfaceSupport) {
		this.interfaceSupport = interfaceSupport;
	}


	public boolean isFullAudioSupport() {
		return fullAudioSupport;
	}



	public void setFullAudioSupport(boolean fullAudioSupport) {
		this.fullAudioSupport = fullAudioSupport;
	}

	public boolean isSubtitles() {
		return subtitles;
	}

	public void setSubtitles(boolean subtitles) {
		this.subtitles = subtitles;
	}


	@Override
	public String toString() {
		return "Language [id=" + id + ", localeCode=" + localeCode + ", language=" + language + ", interfaceSupport="
				+ interfaceSupport + ", fullAudioSupport=" + fullAudioSupport + ", subtitles=" + subtitles + "]";
	}
    
}