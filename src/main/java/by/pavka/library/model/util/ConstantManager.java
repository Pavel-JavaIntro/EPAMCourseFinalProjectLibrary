package by.pavka.library.model.util;

import by.pavka.library.model.LibraryFatalException;
import by.pavka.library.model.service.InitService;
import by.pavka.library.model.service.impl.LibServiceFactory;
import by.pavka.library.model.service.ServiceException;
import by.pavka.library.model.service.impl.ReaderServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * The class downloads from the database and kepps constants
 *
 * @author Pavel Kassitchev
 * @version 1,0
 */
public class ConstantManager {

  private static final Logger LOGGER = LogManager.getLogger(ConstantManager.class.getName());
  private static final Map<Integer, String> locations = new HashMap<>();
  private static final Map<Integer, String> operations = new HashMap<>();
  private static final Map<Integer, String> roles = new HashMap<>();
  public static final String ADMIN = "admin";
  public static final String LIBRARIAN = "librarian";
  public static final String SUBSCRIBER = "subscriber";
  public static final String READER = "reader";
  public static final String GUEST = "guest";
  public static final String DECOMMISSIONED = "decommissioned";
  public static final String ON_HAND = "on hand";
  public static final String DELIVERY_DESK_RESERVE = "delivery desk reserve";
  public static final String READING_HALL_RESERVE = "reading hall reserve";
  public static final int LOCATION_DECOMMISSIONED;
  public static final int LOCATION_ON_HAND;
  public static final int LOCATION_DELIVERY_DESK_RESERVE;
  public static final int LOCATION_READING_HALL_RESERVE;
  public static final int NOT_RESERVED = 0;
  public static final int RESERVED = 1;
  public static final int PREPARED = 2;
  public static final int ISSUED = 3;
  public static final int DECOM = 4;
  public static final long DESK_DISPATCH_TERM = 7;

  private ConstantManager()  {}

  static {
    try {
      InitService service = LibServiceFactory.getInitService();
      service.initConstants(locations, TableEntityMapper.LOCATION);
      service.initConstants(roles, TableEntityMapper.ROLE);
      LOCATION_DECOMMISSIONED = getLocationIdByDescription(DECOMMISSIONED);
      LOCATION_ON_HAND = getLocationIdByDescription(ON_HAND);
      LOCATION_DELIVERY_DESK_RESERVE = getLocationIdByDescription(DELIVERY_DESK_RESERVE);
      LOCATION_READING_HALL_RESERVE = getLocationIdByDescription(READING_HALL_RESERVE);
    } catch (ServiceException e) {
      LOGGER.fatal("Cannot initialize constants");
      throw new LibraryFatalException("Cannot initialize constants");
    }
  }

  public static String getLocationById(int i) {
    return locations.get(i);
  }

  public static int getLocationIdByDescription(String description) {
    for (Map.Entry<Integer, String> entry : locations.entrySet()) {
      if (entry.getValue().equals(description)) {
        return entry.getKey();
      }
    }
    return 0;
  }

  public static String getOperationById(int i) {
    return operations.get(i);
  }

  public static String getRoleById(int i) {
    return roles.get(i);
  }

  public static int getRoleId(String role) {
    for (Map.Entry<Integer, String> entry : roles.entrySet()) {
      if (entry.getValue().equals(role)) {
        return entry.getKey();
      }
    }
    return 0;
  }
}
