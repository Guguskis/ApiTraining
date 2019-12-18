package lt.liutikas.dto;

public class CreatePersonDto {
    private String name;
    private long officialId;
    private long languageId;

    public CreatePersonDto() {
        // Jackson requires default constructor
    }

    public CreatePersonDto(long officialId, String name) {
        this.name = name;
        this.officialId = officialId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getOfficialId() {
        return officialId;
    }

    public void setOfficialId(long officialId) {
        this.officialId = officialId;
    }

    public long getLanguageId() {
        return languageId;
    }

    public void setLanguageId(long languageId) {
        this.languageId = languageId;
    }

    @Override
    public String toString() {
        return "CreatePersonDto{" +
                "name='" + name + '\'' +
                ", officialId=" + officialId +
                ", languageId=" + languageId +
                '}';
    }
}
