package lt.liutikas.model;

public class LanguagePersonDTO {
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getOfficialId() {
        return officialId;
    }

    public void setOfficialId(long officialId) {
        this.officialId = officialId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    private long id;
    private long officialId;
    private String name;
    private String language;
}
