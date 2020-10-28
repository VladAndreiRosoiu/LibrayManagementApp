import dao.DbAuthorDao;
import dao.DbGenreDao;
import models.Library;
import models.book.Author;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

//            Library library = new Library();
//            library.initLibrary();
        List<Author> authorList  = new ArrayList<>();
        List<String> genreList = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        boolean continueAdding = false;
       do {

//           System.out.println("First name");
//           String firstName = sc.next();
//           System.out.println("Last name");
//           String lastName = sc.next();
//           System.out.println("Description");
//
//           String description = sc.next();
//           System.out.println("Birth Date");
//           System.out.println("Year");
//           int year = sc.nextInt();
//           System.out.println("Month");
//           int month = sc.nextInt();
//           System.out.println("Day");
//           int day = sc.nextInt();
//           LocalDate birdate = LocalDate.of(year,month,day);
//           authorList.add(new Author(firstName,lastName,description,birdate));

           System.out.println("Enter genre");
           String genre = sc.nextLine();
           genreList.add(genre);

           System.out.println("Continue? Y/N");
           String answer = sc.next().trim();
           sc.skip("\n");
           continueAdding= answer.equalsIgnoreCase("y");
       }while (continueAdding);

//        DbAuthorDao dbAuthorDao = new DbAuthorDao();
//        List<Integer> generatedID = dbAuthorDao.getInsertedAuthorsIds(authorList);
//        generatedID.forEach(System.out::println);

        DbGenreDao dbGenreDao = new DbGenreDao();
        List<Integer> genreIds = dbGenreDao.getInsertedGenresIds(genreList);
        genreIds.forEach(System.out::println);


    }
}
