package lt.liutikas.dto;

public class CreatePersonDto {
    private String name;
    private long officialId;
    private long languagelId;

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

    public long getLanguagelId() {
        return languagelId;
    }

    public void setLanguagelId(long languagelId) {
        this.languagelId = languagelId;
    }

    @Override
    public String toString() {
        return "CreatePersonDto{" +
                "name='" + name + '\'' +
                ", officialId=" + officialId +
                ", languagelId=" + languagelId +
                '}';
    }
}
