package nextstep.subway.station.dto;

import nextstep.subway.line.domain.Sections;
import nextstep.subway.station.domain.Station;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StationResponses {

    private List<StationResponse> stationResponses;

    public StationResponses() {
    }

    public StationResponses(final List<Station> list) {
        this.stationResponses = list.stream()
                .map(StationResponse::of)
                .collect(Collectors.toList());
    }

    public StationResponses(final Sections sections) {
        stationResponses = new ArrayList<>();
        sections.getSections()
                .forEach(section -> {
                    stationResponses.add(StationResponse.of(section.getUpStation()));
                    stationResponses.add(StationResponse.of(section.getDownStation()));
                });
    }

    public List<StationResponse> getStationResponses() {
        return stationResponses;
    }

    public int size() {
        return stationResponses.size();
    }

}
