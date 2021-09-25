package thelonebarkeeper.mgame.lobby.Data;

public class Data {

    private String playerName;
    private int kills;
    private boolean isWinner = false;
    private int games;
    private int wins;

    public Data(String playerName) {
        this.playerName = playerName;
    }

    public Data(String playerName, int kills, int games, int wins) {
        this.playerName = playerName;
        this.kills = kills;
        this.games = games;
        this.wins = wins;
    }

    public int getKills() {
        return kills;
    }

    public void addKills(int kills) {
        this.kills += kills;
    }

    public void addGame() {this.games++;};

    public void addWin() {this.games++;}


    @Override
    public String toString() {
        return kills + "," + games + "," + wins;
    }


}
