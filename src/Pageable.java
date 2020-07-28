interface Pageable{
	public boolean hasNext();
	public Pageable next();
	// public Pageable next(int pages); // go forward with a number of pages
	// public Pageable prev();          // go backward
	public String getSortBy();
	public int getPageSize();
	public int getOffset();
	public int getTotalPages();
	public int getCurrentPage();
}

class DefaultPagination implements Pageable{
	String sortByField;
	int pageSize;
	int offset;
	int totalCounter;	

	int totalPages;

	public DefaultPagination(int totalCounter) {
		this.offset = 0;
		this.pageSize = 10;
		this.sortByField = "applicant";
 		this.totalCounter = totalCounter;

		if (this.totalCounter % this.pageSize == 0) {
			this.totalPages = this.totalCounter / this.pageSize;
		}
		else {
			this.totalPages = (this.totalCounter / this.pageSize) + 1;
		}
	}

	public String getSortBy() {
		return this.sortByField;
	}

	public int getPageSize() {
		return this.pageSize;
	}

	public int getOffset() {
		return this.offset;
	}

	public boolean hasNext() {
		return this.offset < this.totalCounter;
	}

	public Pageable next() {
		this.offset += this.pageSize;
		return this;
	}

	public int getTotalPages() {
		return this.totalPages;
	}

	public int getCurrentPage() {
		return (this.offset / this.pageSize) + 1;
	}

}