package todo;

public enum TaskReportSortType {

    TITLE_ASC {
        @Override
        int compare(final TaskReport a, final TaskReport b) {
            return a.getTitle().compareTo(b.getTitle());
        }
    },
    TITLE_DESC {
        @Override
        int compare(final TaskReport a, final TaskReport b) {
            return b.getTitle().compareTo(a.getTitle());
        }
    },
    DATE_ASC {
        @Override
        int compare(final TaskReport a, final TaskReport b) {
            return a.getDate().compareTo(b.getDate());
        }
    },
    DATE_DESC {
        @Override
        int compare(final TaskReport a, final TaskReport b) {
            return b.getDate().compareTo(a.getDate());
        }
    },
    ;

    abstract int compare(final TaskReport a, final TaskReport b);
}
