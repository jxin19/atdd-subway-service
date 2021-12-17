package nextstep.subway.line.application;

import nextstep.subway.line.domain.Distance;
import nextstep.subway.line.domain.Line;
import nextstep.subway.line.domain.LineRepository;
import nextstep.subway.line.domain.Section;
import nextstep.subway.line.dto.LineRequest;
import nextstep.subway.line.dto.LineResponse;
import nextstep.subway.line.dto.LineResponses;
import nextstep.subway.line.dto.SectionRequest;
import nextstep.subway.station.application.StationService;
import nextstep.subway.station.domain.Station;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class LineService {

    private final LineRepository lineRepository;
    private final StationService stationService;

    public LineService(final LineRepository lineRepository, final StationService stationService) {
        this.lineRepository = lineRepository;
        this.stationService = stationService;
    }

    public LineResponse saveLine(final LineRequest request) {
        Station upStation = stationService.findById(request.getUpStationId());
        Station downStation = stationService.findById(request.getDownStationId());
        Line line = Line.of(request, upStation, downStation);
        Line persistLine = lineRepository.save(line);
        return LineResponse.of(persistLine);
    }

    public LineResponses findLines() {
        List<Line> persistLines = lineRepository.findAll();
        return new LineResponses(persistLines);
    }

    public LineResponse findLineResponseById(final Long id) {
        Line persistLine = findLineById(id);
        return LineResponse.of(persistLine);
    }

    public void updateLine(final Long id, final LineRequest lineUpdateRequest) {
        Line persistLine = lineRepository.findById(id).orElseThrow(RuntimeException::new);
        persistLine.update(new Line(lineUpdateRequest.getName(), lineUpdateRequest.getColor()));
    }

    public void deleteLineById(final Long id) {
        lineRepository.deleteById(id);
    }

    public void addLineStation(final Long lineId, final SectionRequest request) {
        Line line = findLineById(lineId);
        Section section = this.getNewSection(line, request.getUpStationId(), request.getDownStationId(), new Distance(request.getDistance()));
        line.addSection(section);
    }

    private Section getNewSection(final Line line,final Long upStationId, final Long downStationId, final Distance distance) {
        Station upStation = stationService.findById(upStationId);
        Station downStation = stationService.findById(downStationId);
        return new Section(line, upStation, downStation, distance);
    }

    public void removeLineStation(final Long lineId, final Long stationId) {
        Line line = findLineById(lineId);
        Station station = stationService.findById(stationId);
        line.deleteStation(station);
    }

    private Line findLineById(final Long id) {
        return lineRepository.findById(id)
                .orElseThrow(RuntimeException::new);
    }

}
