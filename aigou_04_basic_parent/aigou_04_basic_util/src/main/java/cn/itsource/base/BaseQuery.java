package cn.itsource.base;

public class BaseQuery {
    private Integer page = 1;
    private Integer rows = 10;
    private String keyword;

    public Integer getStart() {
        return (page-1)*rows;
    }
    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public String toString() {
        return "BaseQuery{" +
                "page=" + page +
                ", rows=" + rows +
                ", keyword='" + keyword + '\'' +
                '}';
    }
}
