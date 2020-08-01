package sample.data;

public class Sportsman {
    private String fullName;
    private String structure;
    private String position;
    private int title;
    private String kindOfSport;
    private String category;

    public void setStructure(int structure) {
        switch (structure) {
            case 0: {
                this.structure = Constant.MAIN;
                break;
            }
            case 1: {
                this.structure = Constant.SPARE;
                break;
            }
            default: {
                this.structure = Constant.NA;
            }
        }
    }

    public void setCategory(int category) {
        switch (category) {
            case 0: {
                this.category = Constant.MASTER_OF_SPORT;
                break;
            }
            case 1: {
                this.category = Constant.СMS;
                break;
            }
            case 2: {
                this.category = Constant.DISCHARGE3;
                break;
            }
            case 3: {
                this.category = Constant.DISCHARGE2;
                break;
            }
            default: {
                this.category = Constant.DISCHARGE1;
            }
        }
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public void setKindOfSport(String kindOfSport) {
        this.kindOfSport = kindOfSport;
    }

    public Sportsman(String fullName, int structure, String position, int title, String kindOfSport, int category) {
        setFullName(fullName);
        setStructure(structure);
        setPosition(position);
        setTitle(title);
        setKindOfSport(kindOfSport);
        setCategory(category);

    }

    public int getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public String getFullName() {
        return fullName;
    }

    public String getKindOfSport() {
        return kindOfSport;
    }

    public String getPosition() {
        return position;
    }

    public String getStructure() {
        return structure;
    }

    public String toString() {
        String result = "";

        return result;
    }
}
