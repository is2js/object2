package menu;

public enum CompositeMenuSortType {
    TITLE_ASC{
        @Override
        int compare(final CompositeMenu a, final CompositeMenu b) {
            return a.getTitle().compareTo(b.getTitle());
        }
    },
    TITLE_DESC {
        @Override
        int compare(final CompositeMenu a, final CompositeMenu b) {
            return b.getTitle().compareTo(a.getTitle());
        }
    },
    DATE_ASC {
        @Override
        int compare(final CompositeMenu a, final CompositeMenu b) {
            return a.getDate().compareTo(b.getDate());
        }
    },
    DATE_DESC {
        @Override
        int compare(final CompositeMenu a, final CompositeMenu b) {
            return b.getDate().compareTo(a.getDate());
        }
    },
    ;

    abstract int compare(final CompositeMenu a, final CompositeMenu b);
}
