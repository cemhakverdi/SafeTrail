package com.example.safetrail;


import java.util.ArrayList;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * This class takes enables us to access the database and use it
 * @authors Cem Hakverdi
 */
public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private static DatabaseAccess instance;
    Cursor cursor;
    Cursor cursor2;
    Cursor cursor3;
    Cursor cursor4;

    /**
     * Creating a new database helper with given context
     * @param context is the context of the application
     */
    private DatabaseAccess(Context context)
    {
        this.openHelper = new DatabaseHelper(context);
        cursor = null;
        cursor2 = null;
        cursor3 = null;
        cursor4 = null;
    }


    /**
     * Creating a databaseaccess object to enable user to use database of the system and its methods
     * @param context is the context of the application
     * @return
     */
    public static DatabaseAccess getInstance(Context context)
    {
        if(instance == null)
        {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    /**
     * This methods opens the existing database and let user to use it
     */
    public void open()
    {
        this.db = openHelper.getWritableDatabase();
    }


    /**
     * This method close the database
     */
    public void close()
    {
        if(db != null)
        {
            this.db.close();
        }
    }



    // Train Methods


    /**
     * This method insert data to Train table in the database
     * This method acts as a constructor of the train object
     * @param trainID it's the id of the train
     * @param lineID it's Line id that train rides in
     * @param trainTime it's the departure time of the train
     * @param trainYear it's the departure year of the train
     * @param trainMonth it's the departure month of the train
     * @param trainDay it's the departure day of the train
     * @param trainDirection it's direction of the train on the line
     * @param isDepartured it's mention that train is departed or not
     */
    public void insertDataToTrainTable(int trainID, int lineID, double trainTime, int trainYear, int trainMonth, int trainDay, int trainDirection,int isDepartured)
    {
        db.execSQL("INSERT INTO Train VALUES (" + trainID +  ", " + lineID + ", " + trainTime + ", " + trainYear + ", " + trainMonth +  "," + trainDay + "," + trainDirection + "," + isDepartured + ")");
    }


    /**
     * This method deletes the given train from database
     * @param trainID it's the id of the train
     */
    public void deleteTrain(int trainID)
    {
        db.execSQL("Delete from Train where TrainID = " + trainID);
        db.execSQL("Delete from Wagon where TrainID = " + trainID);
    }


    /**
     * This method changes the isDeparted variable of the train and start the ride
     * @param trainID it's the id of the train
     */
    public void departTheTrain(int trainID)
    {
        db.execSQL("update Train set isDepartured = 1 where TrainID = " + trainID);
    }

    /**
     * this method sets a new schedule for the trains that haven't departed and set isDeparted variable of the train as 0
     * @param trainID it's the id of the train
     * @param time it's departure time of the train
     * @param year it's the departure year of the train
     * @param month it's the departure month of the train
     * @param day it's the departure day of the train
     */
    public void setSchedule(int trainID, double time, int year, int month, int day)
    {
        db.execSQL("update train set TrainTime = " + time + " , TrainYear = " + year + " , TrainMonth = " + month + " , TrainDay = " + day + " where TrainID = " + trainID);
        db.execSQL("update Train set isDepartured = 0 where TrainID = " + trainID);
    }


    /**
     * This method show all of the trains in the database
     * @return train id and line id of all trains
     */
    public ArrayList<String> viewAllTrains()
    {
        ArrayList<String> listText = new ArrayList<>();

        cursor = db.rawQuery("Select TrainID, LineID from Train", new String[]{});
        if(cursor.getCount() == 0)
        {
            return null;
        }
        else
        {
            while(cursor.moveToNext())
            {
                listText.add("TrainID: " + cursor.getString(0) + "   LineID: " + cursor.getString(1));
            }
            return listText;
        }
    }

    /**
     * this method shows all trains that have departed
     * @return the train id of departed trains
     */
    public ArrayList<String> getDeparturedTrains()
    {
        ArrayList<String> listText = new ArrayList<>();
        listText.add("Select Train");
        cursor = db.rawQuery("select TrainID from Train where isDepartured = 1", new String[]{});
        if(cursor.getCount() == 0)
        {
            listText.add("No Available Trains");
        }
        else
        {
            while(cursor.moveToNext())
            {
                listText.add(cursor.getString(0));
            }
        }
        return listText;
    }

    /**
     * this method shows all trains that have not departed
     * @return the train id of not departed trains
     */
    public ArrayList<String> getUndeparturedTrains()
    {
        ArrayList<String> listText = new ArrayList<>();
        listText.add("Select Train");
        cursor = db.rawQuery("select TrainID from Train where isDepartured = 0", new String[]{});
        if(cursor.getCount() == 0)
        {
            listText.add("No Available Trains");
        }
        else
        {
            while(cursor.moveToNext())
            {
                listText.add(cursor.getString(0));
            }
        }
        return listText;

    }

    /**
     * This method gives the number or departed trains
     * @return the number of departed trains
     */
    public String getNumberOfTrainsDeparted()
    {
        cursor = db.rawQuery("select count(TrainID) from train where Train.IsDepartured = 1", new String[]{});
        cursor.moveToNext();
        return cursor.getString(0);
    }

    /**
     * This method gives the departure time of the train
     * @param trainID is id of the train
     * @return the departure time of the given train
     */
    public String getTrainTime(String trainID)
    {
        String trainTime = "";
        cursor = db.rawQuery("select TrainTime from train where TrainID= '" + trainID+"'",new String[]{});
        while (cursor.moveToNext()) {
            trainTime = cursor.getString(0);
        }
        return trainTime;
    }


    /**
     * This method gives whether the train is departed or not
     * @param trainID is the id of the train
     * @return true if the given train is departed, false otherwise
     */
    public boolean isDeparted(String trainID)
    {
        cursor = db.rawQuery("select isDepartured from Train where TrainID = '" + trainID + "'",new String[]{});
        cursor.moveToNext();
        return cursor.getString(0).equals("1");
    }


    /**
     * This method gives id of all trains
     * @return the id of the trains that are in the database
     */
    public ArrayList<String> getAllTrains()
    {
        ArrayList<String> listText = new ArrayList<>();

        cursor = db.rawQuery("Select TrainID from Train", new String[]{});
        if(cursor.getCount() == 0)
        {
            return null;
        }
        else
        {
            while(cursor.moveToNext())
            {
                listText.add(cursor.getString(0));
            }
            return listText;
        }
    }


    /**
     * Check that the given train is exist in the database
     * @param trainID is the id of the train
     * @return true if the train is already exist in database, false otherwise
     */
    public boolean isTrainExist(String trainID)
    {
        cursor = db.rawQuery("select Count(TrainID) from Train where TrainID = '" + trainID + "'",new String[]{});
        cursor.moveToNext();
        return cursor.getString(0).equals("1");
    }


    /**
     * This method gives the wagons of the given train
     * @param trainID is the id of the train
     * @param isFirstClass 1 for first class trains, 0  for economy class trains
     * @return the wagon number of the trains
     */
    public ArrayList<String> getWagonsOfTheTrain(String trainID, int isFirstClass)
    {
        ArrayList<String> wagonArray = new ArrayList<>();
        cursor = db.rawQuery("Select WagonNumber from wagon INNER join Train on Wagon.TrainID = Train.TrainID where Wagon.TrainID = '"  + trainID + "' and Wagon.WagonFirstClass = " + isFirstClass, new String []{});
        while(cursor.moveToNext())
        {
            wagonArray.add(cursor.getString(0));
        }
        return wagonArray;
    }

    /**
     * This method finds the trains for specific departure city, destination city and date
     * @param departureCity is the city that train will departure
     * @param destinationCity is the destination city of the train
     * @param Day is the departure day of the train
     * @param Month is the departure month of the train
     * @param Year is the departure year of the train
     * @return a ArrayList that have suitable trains
     */
    public ArrayList<String> getSuitableTrains(String departureCity,String destinationCity, String Day, String Month, String Year)
    {
        int trainDirection = -1;
        ArrayList<String> trainIDList = new ArrayList<>();
        ArrayList<String> trainReturnList = new ArrayList<>();
        ArrayList<String> trainList = new ArrayList<>();
        cursor = db.rawQuery( "select Line.LineID,Station.LineRank from Line INNER join Station on Station.LineID = Line.LineID where Station.StationName= '"+departureCity+"'"+ "and Line.LineID in (select Line.LineID from Line inner join Station on Station.LineID = Line.LineID where StationName = '"+destinationCity+"')", new String[]{});
        while (cursor.moveToNext()) {
            String lineID = cursor.getString(0);
            String departureCityRank = cursor.getString(1);
            cursor2 = db.rawQuery("select LineRank from Station where StationName = '"+destinationCity+"'"+" and LineID = '" + lineID + "'",new String[]{});
            while (cursor2.moveToNext()) {
                String destinationCityRank = cursor2.getString(0);
                int dcrInt = Integer.parseInt(departureCityRank);
                int descrInt = Integer.parseInt(destinationCityRank);
                if (dcrInt < descrInt) {
                    trainDirection = 1;
                }
                else if (dcrInt > descrInt) {
                    trainDirection = 0;
                }
            }
            cursor3 = db.rawQuery("select TrainID from train where LineID ='" + lineID + "'" + "and TrainDirection = " + trainDirection,new String[]{});
            if (trainDirection == -1) {
                ArrayList<String> emptyArray = new ArrayList<>();
                emptyArray.add("There are no suitable trains");
                return emptyArray;
            }
            while (cursor3.moveToNext()) {
                trainIDList.add(cursor3.getString(0));
            }
            for (int i = 0; i < trainIDList.size() ;i++) {
                cursor4 = db.rawQuery("Select TrainDay,TrainMonth,TrainYear from Train where TrainID = " + "'" + trainIDList.get(i) + "'", new String[]{});
                while (cursor4.moveToNext()) {
                    if (Day.equals(cursor4.getString(0)) && Month.equals(cursor4.getString(1)) && Year.equals(cursor4.getString(2))) {
                        if (!trainReturnList.isEmpty()) {
                            for (int j = 0; j < trainReturnList.size();j++) {
                                if (!trainReturnList.get(j).equals(trainIDList.get(i))) {
                                    trainReturnList.add(trainIDList.get(i));
                                }
                            }
                        }
                        else {
                            trainReturnList.add(trainIDList.get(i));
                        }
                    }
                }
            }



        }
        return trainReturnList;
    }





    //Line Methods

    /**
     * This method inserts data to line table
     * @param lineID is the id of the line
     * @param lineLength is the length of the line
     */
    public void insertDataToLineTable(int lineID, int lineLength)
    {
        db.execSQL("insert into Line values (" + lineID + ", " + lineLength + ")");
    }

    /**
     * This method deletes the line from database and stations that connected to that line and set line id of the trains 0 on that line
     * @param lineID is id of the line
     */
    public void deleteLine(int lineID)
    {
        db.execSQL("update Train set LineID = 0 where LineID = " + lineID);
        db.execSQL("delete from Station where LineID = " + lineID);
        db.execSQL("delete from Line where LineID = " + lineID);
    }

    /**
     * This method gives all lines in the database
     * @return the id of the all lines
     */
    public ArrayList<String> getAllLines()
    {
        ArrayList<String> listText = new ArrayList<>();
        listText.add("Select Lines");
        cursor = db.rawQuery("select LineID from Line ", new String[]{});
        if(cursor.getCount() == 0)
        {
            listText.add("No Lines");
        }
        else
        {
            while(cursor.moveToNext())
            {
                listText.add(cursor.getString(0));
            }
        }
        return listText;

    }


    /**
     * This method gives all lines in the database
     * @return the id and length of the all lines
     */
    public ArrayList<String> viewAllLines()
    {
        ArrayList<String> listText = new ArrayList<>();
        listText.add("Select Lines");
        cursor = db.rawQuery("select LineID, LineLength from Line ", new String[]{});
        if(cursor.getCount() == 0)
        {
            listText.add("No Lines");
        }
        else
        {
            while(cursor.moveToNext())
            {
                listText.add("Line ID : " + cursor.getString(0) + "  Length of the Line: "  + cursor.getString(1) );
            }
        }
        return listText;

    }

    /**
     * This method gives stations of the given line
     * @param lineID is id of the line
     * @return station name and station rank of all stations in that line
     */
    public ArrayList<String> viewStationsOfTheLine(String lineID)
    {
        ArrayList<String> listText = new ArrayList<>();
        cursor = db.rawQuery("Select StationName, LineRank from Station where LineID = '" + lineID + "' order by LineRank", new String[]{});
        while(cursor.moveToNext())
        {
            listText.add("Station Name: " + cursor.getString(0) + " Line Rank: " + cursor.getString(1));
        }

        return listText;
    }


    /**
     * This method checks whether the given line exist or not
     * @param lineID is id of the line
     * @return true if the line already exist
     */
    public boolean isLineExist(String lineID)
    {
        cursor = db.rawQuery("select Count(LineID) from Line where LineID = '" + lineID + "'",new String[]{});
        cursor.moveToNext();
        return cursor.getString(0).equals("1");
    }

    /**
     * This method checks whether the station for a given line exist or not
     * @param stationName is the station name
     * @param lineID is id of the line
     * @return true if the station on that line exist, false otherwise
     */
    public boolean isStationExist(String stationName, String lineID)
    {
        cursor = db.rawQuery("Select count(StationName), count(StationID) from station inner join Line on Station.LineID = Line.LineID where Station.StationName = '" + stationName + "' and Line.LineID = '" + lineID + "'", new String[]{});
        cursor.moveToNext();
        return cursor.getInt(0) > 0 || cursor.getInt(1) > 0;

    }






    //Wagon Methods

    /**
     * This method insert data to wagon table
     * @param wagonNumber is the number of the wagon
     * @param seatAmount is the number of seats
     * @param customerAmount is the amount of the customers
     * @param wagonFirstClass 1 for first class, 0 for economy class
     * @param trainID is id of the train we want to add wagon
     */
    public void insertDataToWagonTable(int wagonNumber, int seatAmount, int customerAmount, int wagonFirstClass, int trainID)
    {
        db.execSQL("INSERT INTO Wagon VALUES (" + wagonNumber +  ", " + seatAmount + ", " + customerAmount + ", " + wagonFirstClass + ", " + trainID + ")");
    }


    /**
     * This method deletes wagon from the wagon table
     * @param trainID is id of the train
     * @param wagonNumber is the number of the wagon
     */
    public void deleteWagon(int trainID, int wagonNumber)
    {
        db.execSQL("DELETE FROM Wagon WHERE TrainID = " + trainID + " and WagonNumber = " + wagonNumber);
    }


    /**
     * This method get activated when somebody buy a ticket and incerease the customer amount on that wagon by 1
     * @param wagonNumber is the number of the wagon
     */
    public void increaseCustomerAmount(String wagonNumber)
    {
        int customerAmount;
        cursor = db.rawQuery("Select CustomerAmount from Wagon where WagonNumber = '" + wagonNumber + "'", new String[]{});
        cursor.moveToNext();
        customerAmount = cursor.getInt(0);
        customerAmount += 1;

        db.execSQL("Update Wagon set CustomerAmount = " + customerAmount + " where wagonNumber = '" + wagonNumber + "'");
    }

    /**
     * This method gives the number of customers from departed trains
     * @return  the number of the customers who traveled
     */
    public String getNumbersOfCustomers()
    {
        cursor = db.rawQuery("select sum(CustomerAmount) from wagon inner join Train on Train.TrainID = Wagon.TrainID where Train.IsDepartured = 1",new String[]{});
        cursor.moveToNext();
        return cursor.getString(0);
    }


    /**
     * This method checks whether the given wagon exist in database or not
     * @param wagonNumber is the number of the wagon
     * @return true if the wagon exist, false otherwise
     */
    public boolean isWagonExist(String wagonNumber)
    {
        cursor = db.rawQuery("select Count(WagonNumber) from Wagon where WagonNumber = '" + wagonNumber + "'",new String[]{});
        cursor.moveToNext();
        return cursor.getString(0).equals("1");
    }


    /**
     * This method gives an ArrayList that contains occupied seat numbers
     * @param wagonNumber is the number of the wagon
     * @return an ArrayList of occupied seat numbers
     */
    public ArrayList<String> getOccupiedSeats( String wagonNumber)
    {
        ArrayList<String> seatArray = new ArrayList<>();
        cursor = db.rawQuery("Select SeatNumber from Seat inner join Wagon on Seat.WagonNumber = Wagon.WagonNumber where Wagon.WagonNumber = '" + wagonNumber + "' and Seat.isOccupied = 1" , new String []{});
        while(cursor.moveToNext())
        {
            seatArray.add(cursor.getString(0));
        }
        return seatArray;
    }





    //Station Methods

    /**
     * This method insert data to station table
     * @param stationID is id of the station
     * @param stationName is name of the station
     * @param lineID is id of the line that contains the station
     * @param lineRank rank of the line that describes which station is that station on that line
     */
    public void insertDataToStationTable(int stationID, String stationName, int lineID, int lineRank)
    {
        db.execSQL("insert into Station values (" + stationID + ", '" + stationName + "' ," + lineID + "," + lineRank + ")");
    }


    /**
     * This method delete station from the station table
     * @param lineID is id of the line that contains the station
     * @param stationName is id of the station
     */
    public void deleteStation(int lineID, String stationName)
    {
        db.execSQL("delete from Station where LineID = " + lineID + " and StationName = '" + stationName + "'");
    }

    /**
     * This method gives the rank of the station
     * @param wagonNumber is number of the wagon
     * @param stationName is name of the station
     * @return the rank of the station
     */
    public String getLineRank(String wagonNumber, String stationName)
    {
        cursor = db.rawQuery("Select LineRank from station INNER JOIN Line on Station.LineID = Line.LineID inner join Train on Train.LineID = Line.LineID INNER JOIN Wagon on Train.TrainID = Wagon.TrainID where Wagon.WagonNumber = '" + wagonNumber + "' and StationName = '" + stationName + "'", new String[]{});
        cursor.moveToNext();
        return cursor.getString(0);
    }

    /**
     * This method gives all stations in an ArrayList
     * @return the list of stations
     */
    public ArrayList<String> viewAllCities()
    {
        ArrayList<String> listText = new ArrayList<>();
        cursor = db.rawQuery("select StationName from Station ", new String[]{});
        if(cursor.getCount() == 0)
        {
            listText.add("No Station");
        }
        else
        {
            while(cursor.moveToNext())
            {
                listText.add(cursor.getString(0));
            }
        }
        return listText;

    }


    //Seat Methods

    /**
     * This method create seats for the wagon depending on the wagon's class
     * @param wagonNumber is number of the wagon we want to create seats for
     * @param isFirstClass 1 for first class, 0 for eceonmy class
     */
    public void createSeats(String wagonNumber, String isFirstClass)
    {
        if(Integer.parseInt(isFirstClass) == 1)
        {
            for(int i = 1; i < 11; i++)
            {
                db.execSQL("Insert into Seat Values (" + i + ", 0, '" + wagonNumber + "' )" );
            }
        }

        else
        {
            for(int i = 1; i < 21; i++)
            {
                db.execSQL("Insert into Seat Values (" + i + ", 0, '" + wagonNumber + "' )" );
            }
        }
    }


    /**
     * This method gets activated when customer buy a ticket and occupy the choosen seat
     * @param seatNumber is number of the seat
     * @param wagonNumber number of the wagon
     */
    public void occupyTheSeat(String seatNumber, String wagonNumber)
    {
        db.execSQL("UPDATE Seat set isOccupied = 1 where SeatNumber = '" + seatNumber + "' and WagonNumber = '" + wagonNumber + "'");
    }


    /**
     * This method checks whether the line has that line rank or not
     * @param lineRank is the line rank we want to check
     * @param lineID is id of the line
     * @return true if this line rank exist, false otherwise
     */
    public boolean isLineRankedStationExist(String lineRank, String lineID)
    {
        cursor = db.rawQuery("Select count(LineRank) from Station where LineRank = '" + lineRank + "' and LineID = '" + lineID + "'", new String[]{});
        cursor.moveToNext();
        return cursor.getInt(0) > 0;
    }





    //Credit Card Methods


    /**
     * This method inserts data to credit card table in database
     * @param userName user name of the customer
     * @param cardNumber number of the credit card
     * @param cardDate date of the credit card
     * @param cardCvv cvv of the credit card
     * @param nameOnTheCard name on the credit card
     */
    public void insertIntoCreditCardTable(String userName,String cardNumber, String cardDate, int cardCvv, String nameOnTheCard)
    {
        db.execSQL("insert into CreditCard values ( '" + cardNumber + "', '" + cardDate + "', " + cardCvv + ", '" + nameOnTheCard + "' )");
        db.execSQL("update User set CardNumber = '" + cardNumber + "'" + "where UserName = '" + userName + "'" );
    }

    /**
     * Gives credit card number of the customer
     * @param userName is user name of the customer
     * @return the saved credit card number of the customer
     */
    public String getCreditCardNumber(String userName)
    {
        cursor = db.rawQuery("select CreditCard.CardNumber from CreditCard inner join User on User.CardNumber = CreditCard.CardNumber where User.UserName = '" + userName + "'", new String[]{});
        cursor.moveToNext();
        return cursor.getString(0);
    }


    /**
     * Gives credit card date of the customer
     * @param userName is user name of the customer
     * @return the saved credit card date of the customer
     */
    public String getCreditCardDate(String userName)
    {
        cursor = db.rawQuery("select CreditCard.CardDate from CreditCard inner join User on User.CardNumber = CreditCard.CardNumber where User.UserName = '" + userName + "'", new String[]{});
        cursor.moveToNext();
        if(cursor.getString(0) == null || cursor.getCount() < 0)
        {
            return "0";
        }
        return cursor.getString(0);

    }

    /**
     * Gives credit card cvv of the customer
     * @param userName is user name of the customer
     * @return the saved credit card cvv of the customer
     */
    public String getCreditCardCvv(String userName)
    {
        cursor = db.rawQuery("select CreditCard.CardCVV from CreditCard inner join User on User.CardNumber = CreditCard.CardNumber where User.UserName = '" + userName + "'", new String[]{});
        cursor.moveToNext();
        if(cursor.getString(0) == null || cursor.getCount() < 0)
        {
            return "0";
        }
        return cursor.getString(0);
    }


    /**
     * Gives name on the credit card from customer's saved card
     * @param userName is user name of the customer
     * @return the name on the credit card from customer's saved card
     */
    public String getCustomerName(String userName)
    {
        cursor = db.rawQuery("select CreditCard.NameOnTheCard from CreditCard inner join User on User.CardNumber = CreditCard.CardNumber where User.UserName = '" + userName + "'", new String[]{});
        if(cursor != null &&  cursor.getCount() > 0)
        {
            cursor.moveToNext();
        }
        if(cursor.getString(0) == null || cursor.getCount() < 0 )
        {
            return "0";
        }

        return cursor.getString(0);
    }


    /**
     * This method checks whether the credit card exist or not
     * @param cardNumber is the card number we want to check
     * @return 1 if the credit card exist, 0  if it's not
     */
    public int checkTheCardNumber(String cardNumber)
    {
        cursor = db.rawQuery("Select count(CreditCard.CardNumber) from CreditCard where CardNumber = '" + cardNumber + "'", new String[]{});
        cursor.moveToNext();
        return cursor.getInt(0);
    }

    /**
     * This method get credit card number from user table
     * @param userName is the user name of the customer
     * @return the credit card number
     */
    public String getCreditCardFromUserTable(String userName)
    {
        cursor = db.rawQuery("Select CardNumber from user where UserName = '" + userName + "'", new String[]{});
        cursor.moveToNext();
        return cursor.getString(0);
    }






    //User Methods


    /**
     * This method inserts data to User table
     * @param userName is user name of the user
     * @param password is password of the user
     * @param customerName is full name of the customer
     * @param favoriteTicketID is the favorite ticket id of the customer
     * @param lastTicketID is the last ticket id of the customer
     * @param isBanned 1 if the customer banned, 0  otherwise
     * @param cardNumber is the card number of the customer
     * @param hesCode is hes code of the customer
     * @param discount is the discount amount
     */
    public void insertDataToUserTable(String userName, String password, String customerName, int favoriteTicketID, int lastTicketID, int isBanned, int cardNumber, String hesCode,int discount)
    {
        db.execSQL("INSERT INTO User VALUES ( '" + userName + "' , '" + password + "' , '" + customerName + "' , " + favoriteTicketID + ", " + lastTicketID + ", " + isBanned + ", " + cardNumber + ", '" + hesCode + "', "+ discount + ")");
    }

    /**
     * This method gives discount to the customer
     * @param userName is user name that we want to give discount
     * @param discountAmount is the discount amount that we want to give
     */
    public void giveDiscount(String userName, String discountAmount)
    {
        db.execSQL("update User set Discount = '" + discountAmount + "' where UserName = " + "'" + userName + "'");
    }


    /**
     * This method bans the customer from the system
     * @param userName is user name of the customer that we want to ban
     */
    public void banCustomer(String userName)
    {
        if(checkUserBanned(userName))     //return true if not banned
        {
            db.execSQL("update User set isBanned = 1 Where UserName = " + "'" + userName + "'");
        }
    }

    /**
     * This method remove the ban from the banned customer
     * @param userName is user name that the customer we want to remove ban
     */
    public void removeBan(String userName)
    {
        if(!checkUserBanned(userName))
        {
            db.execSQL("update User set isBanned = 0 where UserName = " + "'" + userName + "'");
        }
    }


    /**
     * This method set the favorite ticket of the customer
     * @param favTicketID is the id of the favorite ticket that we will insert to table user table
     * @param userName is the user name of the customer
     */
    public void setFavTicket(String favTicketID,String userName)
    {
        db.execSQL("update User set FavoriteTicket1ID = '" + favTicketID + "' where UserName = '" + userName + "'");
    }


    /**
     * This method gives an ArrayList that contains all of the customers
     * @return user name and full name of all customers
     */
    public ArrayList<String> viewAllCustomers()
    {
        ArrayList<String> listText = new ArrayList<>();

        cursor = db.rawQuery("Select UserName,CustomerName from User", new String[]{});
        if(cursor.getCount() == 0)
        {
            return null;
        }
        else
        {
            while(cursor.moveToNext())
            {
                listText.add("User Name: " + cursor.getString(0) + "\nCustomer Name: " + cursor.getString(1));
            }
            return listText;
        }
    }


    /**
     * This method checks whether the user is banned or not
     * @param userName is user name of the customer
     * @return true if the user is not banned, false otherwise
     */
    public boolean checkUserBanned(String userName)
    {
        cursor = db.rawQuery("select isBanned from User where UserName =  " + "'" + userName + "'",new String[]{});
        cursor.moveToNext();
        return Integer.parseInt(cursor.getString(0)) == 0;
    }

    /**
     * This method checks whether the user is exist or not
     * @param userName is user name of the customer
     * @param password is password of the customer
     * @return true if the user exist, false otherwise
     */
    public boolean checkUserExist(String userName, String password)
    {
        cursor = db.rawQuery("select UserName from User where UserName = " + "'" + userName + "'",new String []{});
        cursor2 = db.rawQuery("select Password from User where Password = " + "'" + password + "'", new String[]{});

        return cursor.getCount() > 0 && cursor2.getCount() > 0;
    }


    /**
     * This method gives the last ticket of the customer
     * @param userName is the user name of the customer
     * @return last ticket id
     */
    public String getLastTicket(String userName)
    {
        cursor = db.rawQuery("Select LastTicketID from User where UserName = " + "'" + userName + "'", new String[]{});
        cursor.moveToNext();
        return cursor.getString(0);
    }

    /**
     * This method gives the last ticket of the customer
     * @param userName is the user name of the customer
     * @return last ticket id
     */
    public String getFavTicket(String userName)
    {
        cursor = db.rawQuery("Select FavoriteTicket1ID from User where UserName = " + "'" + userName + "'", new String[]{});
        cursor.moveToNext();
        return cursor.getString(0);
    }


    /**
     * This method deletes the favorite ticket of the user
     * @param userName is the user name of the customer
     */
    public void deleteFavTicket(String userName)
    {
        db.execSQL("update User set FavoriteTicket1ID = 0 where User.UserName = '" + userName + "'");
    }

    /**
     * This method deletes the last ticket of the user
     * @param userName is the user name of the customer
     */
    public void deleteLastTicket(String userName)
    {
        db.execSQL("update User set LastTicketID = 0 where User.UserName = '" + userName + "'");
    }

    /**
     * This method gives the full name of the user
     * @param userName is the user name of the customer
     * @return the full name of the customer
     */
    public String getName(String userName)
    {
        cursor = db.rawQuery("Select CustomerName from User where UserName = " + "'" + userName + "'", new String[]{});
        cursor.moveToNext();
        return cursor.getString(0);
    }


    /**
     * This method gives the hes code of the user
     * @param userName is the user name of the customer
     * @return the hes code of the customer
     */
    public String getHesCode(String userName)
    {
        cursor = db.rawQuery("Select HesCode from User where UserName = " + "'" + userName + "'", new String[]{});
        cursor.moveToNext();
        return cursor.getString(0);
    }

    /**
     * This method checks whether the user name exist or not
     * @param userName is the user name of the customer we want to check
     * @return true if the given user name exist, false otherwise
     */
    public boolean checkUserNameExist(String userName)
    {
        cursor = db.rawQuery("select UserName from User where UserName = " + "'" + userName + "'",new String []{});

        return cursor.getCount() > 0;
    }






    //Ticket Methods


    /**
     * This method inserts data to ticket table
     * @param userName is the user name of the customer
     * @param ticketID is the id of the ticket
     * @param Price is the price of the ticker
     * @param time is the departure time of the ticket
     * @param seatNumber is number of the seat that ticket occupies
     * @param departureCity is the departure city of the train that customer bought ticket for
     * @param destinationCity is the destination city of the train that customer bought ticket for
     * @param wagonNumber is the wagon number of the train that customer bought ticket for
     */
    public void createTicket(String userName, String ticketID, int Price, String time, String seatNumber, String departureCity, String destinationCity, String wagonNumber)
    {
        db.execSQL("insert into Ticket Values ( '" + userName + "', '" + ticketID + "' , " + Price + ", '" + time + "', '"  + seatNumber + "', '" + departureCity + "', '" + destinationCity + "', '" + wagonNumber + "')");
        db.execSQL("update User set LastTicketID = '" + ticketID + "' where UserName = '" + userName + "'");
    }

    /**
     * This method delete the ticket from ticket table and set last ticket of the customer 0 and set is occupied for the seat 0
     * @param ticketID is the id of the ticket we want to delete
     */
    public void deleteTicket(String ticketID)
    {
        db.execSQL("update seat set isOccupied = 0 where Seat.SeatNumber = (Select SeatNumber from Ticket where TicketID = '" + ticketID + "') and Seat.WagonNumber = (SELECT WagonNumber from Ticket where TicketID = '" + ticketID + "')");
        //db.execSQL("update User set LastTicketID = 0 where User.UserName = (Select Ticket.UserName from Ticket inner join User on User.UserName = Ticket.UserName where Ticket.TicketID = " + "'" + ticketID + "')");
        db.execSQL("delete from Ticket where ticketID = '" + ticketID + "'" );
    }


    /**
     * This method gives the discount amount and helps to calculate the price
     * @param userName is user name of the customer
     * @return the discount amount of that customer
     */
    public String getDiscount(String userName)
    {
        cursor = db.rawQuery("Select Discount from User where UserName = " + "'" + userName + "'", new String[]{});
        cursor.moveToNext();
        return cursor.getString(0);
    }


    /**
     * This method gives an ArrayList that contains all tickets in the database
     * @param userName is user name of the customer
     * @return ticket id, departure city and destination city of the ticket in an ArrayList
     */
    public ArrayList<String> viewAllTickets(String userName)
    {
        ArrayList<String> listText = new ArrayList<>();
        cursor = db.rawQuery("Select TicketID, DepartureCity, DestinationCity from Ticket where UserName = " + "'" + userName + "'", new String[]{});

        if(cursor.getCount() == 0)
        {
            return null;
        }
        else
        {
            while(cursor.moveToNext())
            {
                listText.add("Ticket ID: " + cursor.getString(0) + "   Departure City:  " + cursor.getString(1) + "   Destination City: " + cursor.getString(2));
            }
            return listText;
        }
    }


    /**
     * This method gives an ArrayList of ticket that a specific customer has
     * @param userName is user name of the customer
     * @return ticket id of all tickets that customer has in an ArrayList
     */
    public ArrayList<String> getTicketID(String userName)
    {
        ArrayList<String> listText = new ArrayList<>();
        cursor = db.rawQuery("Select TicketID from Ticket where UserName = " + "'" + userName + "'", new String[]{});
        while(cursor.moveToNext())
        {
            listText.add(cursor.getString(0));
        }
        return listText;
    }


    /**
     * This method gives the departure city of the ticket
     * @param ticketID is the id of the ticket
     * @returnt the departure city
     */
    public String getDepartureCity(int ticketID)
    {
        cursor = db.rawQuery("Select DepartureCity from Ticket where TicketID = " + ticketID, new String[]{});
        cursor.moveToNext();
        return cursor.getString(0);
    }

    /**
     * This method gives the destination city of the ticket
     * @param ticketID is the id of the ticket
     * @returnt the destination city
     */
    public String getDestinationCity(int ticketID)
    {
        cursor = db.rawQuery("Select DestinationCity from Ticket where TicketID = " + ticketID, new String[]{});
        cursor.moveToNext();
        return cursor.getString(0);
    }

    /**
     * This method gives the departure time  of the ticket
     * @param ticketID is the id of the ticket
     * @returnt the departure time
     */
    public String getDepartureTime(int ticketID)
    {
        cursor = db.rawQuery("Select TrainTime from Train inner join Wagon on Train.TrainID = Wagon.TrainID inner join Seat on Seat.WagonNumber = Wagon.WagonNumber inner join Ticket on Seat.SeatNumber = Ticket.SeatNumber where Ticket.TicketID = " + ticketID, new String[]{});
        cursor.moveToNext();
        return cursor.getString(0);
    }

    /**
     * This method gives the departure date of the  ticket
     * @param ticketID is the id of the ticket
     * @returnt the departure day, departure month and departure year of the train
     */
    public String getDepartureDate(int ticketID)
    {
        StringBuffer buffer = new StringBuffer();
        cursor = db.rawQuery("Select TrainDay, TrainMonth, TrainYear from Train inner join Wagon on Train.TrainID = Wagon.TrainID inner join Seat on Seat.WagonNumber = Wagon.WagonNumber inner join Ticket on Seat.SeatNumber = Ticket.SeatNumber where Ticket.TicketID = " + ticketID, new String[]{});
        cursor.moveToNext();
        buffer.append(cursor.getString(0) + "/" + cursor.getString(1) + "/" + cursor.getString(2));
        return buffer.toString();
    }


    /**
     * This method gives the seat number that customer bought ticket for
     * @param ticketID is id of the ticket
     * @return the seat number of the given ticket
     */
    public String getSeat(int ticketID)
    {
        cursor = db.rawQuery("Select SeatNumber from Ticket where TicketID = " + ticketID, new String[]{});
        cursor.moveToNext();
        return cursor.getString(0);
    }


    /**
     * This method gives the number of tickets that are in database
     * @return the total number of tickets
     */
    public String getAllTickets()
    {
        cursor = db.rawQuery("Select count(TicketID) from Ticket", new String[]{});
        cursor.moveToNext();
        return cursor.getString(0);
    }

}


