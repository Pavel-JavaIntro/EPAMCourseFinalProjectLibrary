package by.pavka.library.entity.order;

import by.pavka.library.entity.client.AppClient;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * The class represents a reader order
 *
 * @author Pavel Kassitchev
 * @version 1.0
 */
public class BookOrder {
  private final int userId;
  private final Set<EditionInfo> editionInfoSet;

  public BookOrder(AppClient client) {
    this.userId = client.getId();
    editionInfoSet = new CopyOnWriteArraySet<>(client.getEditionInfos());
    client.getEditionInfos().clear();
  }

  public BookOrder(int id, EditionInfo editionInfo) {
    userId = id;
    editionInfoSet = new CopyOnWriteArraySet<>();
    editionInfoSet.add(editionInfo);
  }

  public int getUserId() {
    return userId;
  }

  public Set<EditionInfo> getEditionInfoSet() {
    return editionInfoSet;
  }

  public BookOrder passBook(EditionInfo editionInfo) {
    editionInfoSet.remove(editionInfo);
    return new BookOrder(userId, editionInfo);
  }
}
