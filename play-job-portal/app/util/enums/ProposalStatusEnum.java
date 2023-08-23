package util.enums;

import lombok.Getter;

@Getter
public enum ProposalStatusEnum {
    DRAFT(0),
    SENT(1),
    REVIEWED(2),
    ACCEPTED(3),
    REJECTED(4);

    private final Integer value;
    ProposalStatusEnum(int value) {
        this.value = value;
    }

}
