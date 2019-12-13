package lt.liutikas.dto;

public class CreatePaymentDto {
    private long amount;
    private long personOfficialId;

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public long getPersonOfficialId() {
        return personOfficialId;
    }

    public void setPersonOfficialId(long personOfficialId) {
        this.personOfficialId = personOfficialId;
    }
}
