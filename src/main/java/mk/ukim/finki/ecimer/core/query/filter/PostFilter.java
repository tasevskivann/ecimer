package mk.ukim.finki.ecimer.core.query.filter;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.validation.constraints.Min;

public class PostFilter {

    private int pageNumber;
    @Min(1)
    private int pageSize;
    private String userUuid;
    private String cityUuid;
    private String municipalityUuid;
    private String title;

    public PostFilter() {

    }

    public PostFilter(int pageNumber, int pageSize, String userUuid, String cityUuid, String municipalityUuid, String title) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.userUuid = userUuid;
        this.cityUuid = cityUuid;
        this.municipalityUuid = municipalityUuid;
        this.title = title;
    }

    public Pageable toPageable(){
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        return PageRequest.of(pageNumber, pageSize, sort);
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    private static boolean isNullOrEmpty(String s) {
        return s != null && !s.isEmpty();
    }

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
    }

    public String getCityUuid() {
        return cityUuid;
    }

    public void setCityUuid(String cityUuid) {
        this.cityUuid = cityUuid;
    }

    public String getMunicipalityUuid() {
        return municipalityUuid;
    }

    public void setMunicipalityUuid(String municipalityUuid) {
        this.municipalityUuid = municipalityUuid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
