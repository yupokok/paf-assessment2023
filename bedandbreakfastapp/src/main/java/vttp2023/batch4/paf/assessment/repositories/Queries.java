package vttp2023.batch4.paf.assessment.repositories;

public class Queries {

    // Task 6: Adding Reservation back into SQL

    public static final String SQL_ADD_USER = """
            insert into users(name, email)
                value (?, ?)
            """;

    public static final String SQL_SAVE_BOOKING = """
            insert into bookings(booking_id, listing_id, duration, name, email)
                value (?, ?, ?, ?, ?)
            """;

    public static final String SQL_COUNT_EMAIL = """
            select count(*) as email_count
                from users
                where email = ?
            """;

    public static final String SQL_FIND_USER_BY_ID = """
               select * from users
                  where email = ?
            """;

}
