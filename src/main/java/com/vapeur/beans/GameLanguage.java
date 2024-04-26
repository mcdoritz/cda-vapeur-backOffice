package com.vapeur.beans;

import java.io.Serializable;

public class GameLanguage implements Serializable {
    private static final long serialVersionUID = 1L;

    private int gameId;
    private int languageId;
    private boolean interfaceSupport;
    private boolean fullAudioSupport;
    private boolean subtitles;

    // Constructeurs
    public GameLanguage() {
    }

    public GameLanguage(int gameId, int languageId, boolean interfaceSupport, boolean fullAudioSupport, boolean subtitles) {
        setGameId(gameId);
        setLanguageId(languageId);
        setInterfaceSupport(interfaceSupport);
        setFullAudioSupport(fullAudioSupport);
        setSubtitles(subtitles);
    }

    // Getters et setters
    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getLanguageId() {
        return languageId;
    }

    public void setLanguageId(int languageId) {
        this.languageId = languageId;
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

    // Méthode toString pour l'affichage
    @Override
    public String toString() {
        return "GameLanguage{" +
                "gameId=" + gameId +
                ", languageId=" + languageId +
                ", interfaceSupport=" + interfaceSupport +
                ", fullAudioSupport=" + fullAudioSupport +
                ", subtitles=" + subtitles +
                '}';
    }
}