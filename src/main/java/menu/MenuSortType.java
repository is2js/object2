package menu;

public enum MenuSortType {

    TITLE_ASC{
        @Override
        int compare(final Menu a, final Menu b) {
            return a.getTitle().compareTo(b.getTitle());
        }
    },
    TITLE_DESC {
        @Override
        int compare(final Menu a, final Menu b) {
            return b.getTitle().compareTo(a.getTitle());
        }
    },
    DATE_ASC {
        @Override
        int compare(final Menu a, final Menu b) {
            return a.getDate().compareTo(b.getDate());
        }
    },
    DATE_DESC {
        @Override
        int compare(final Menu a, final Menu b) {
            return b.getDate().compareTo(a.getDate());
        }
    },
    ;

    abstract int compare(final Menu a, final Menu b);
}
