package nextstep.subway.line.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Distance {

    private static final int MIN_DISTANCE = 0;

    @Column
    private int distance;

    public Distance() {
    }

    public Distance(final int distance) {
        validate(distance);
        this.distance = distance;
    }

    public static Distance of(final int distance) {
        return new Distance(distance);
    }

    private void validate(final int distance) {
        if (MIN_DISTANCE >= distance) {
            throw new IllegalArgumentException("입력한 구간의 길이가 옳바르지 않습니다.");
        }
    }

    public Distance add(final Distance distance) {
        return new Distance(this.distance + distance.getDistance());
    }

    public Distance subtract(final Distance distance) {
        return new Distance(this.distance - distance.getDistance());
    }

    public int getDistance() {
        return distance;
    }

}
