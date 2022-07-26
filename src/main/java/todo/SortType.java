package todo;

public enum SortType {

    TITLE_ASC {
        @Override
        int compare(final Task a, final Task b) {
            return a.getTitle().compareTo(b.getTitle());
        }
    },
    TITLE_DESC {
        @Override
        int compare(final Task a, final Task b) {
            return b.getTitle().compareTo(a.getTitle());
        }
    },
    DATE_ASC {
        @Override
        int compare(final Task a, final Task b) {
            return a.getDate().compareTo(b.getDate());
        }
    },
    DATE_DESC {
        @Override
        int compare(final Task a, final Task b) {
            return b.getDate().compareTo(a.getDate());
        }
    },
    ;

    abstract int compare(final Task a, final Task b);
}
