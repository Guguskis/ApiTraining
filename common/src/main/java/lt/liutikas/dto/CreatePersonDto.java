package lt.liutikas.dto;

public class CreatePersonDto {
    private String name;
    private long officialId;
    private long languageId;

    // MM: Is this constructor necessary?
    public CreatePersonDto() {
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
