package org.springframework.data.rest.repository.context;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.data.rest.repository.JpaRepositoryMetadata;

/**
 * @author Jon Brisbin <jon@jbrisbin.com>
 */
public abstract class AbstractRepositoryEventListener<T extends AbstractRepositoryEventListener<? super T>>
    implements ApplicationListener<RepositoryEvent>,
               ApplicationContextAware {

  @Autowired
  protected JpaRepositoryMetadata repositoryMetadata;
  protected ApplicationContext applicationContext;

  @Override public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }

  public JpaRepositoryMetadata getRepositoryMetadata() {
    return repositoryMetadata;
  }

  public void setRepositoryMetadata(JpaRepositoryMetadata repositoryMetadata) {
    this.repositoryMetadata = repositoryMetadata;
  }

  public JpaRepositoryMetadata repositoryMetadata() {
    return repositoryMetadata;
  }

  @SuppressWarnings({"unchecked"})
  public T repositoryMetadata(JpaRepositoryMetadata repositoryMetadata) {
    this.repositoryMetadata = repositoryMetadata;
    return (T) this;
  }

  @Override public final void onApplicationEvent(RepositoryEvent event) {
    if (event instanceof BeforeSaveEvent) {
      onBeforeSave(event.getSource());
    } else if (event instanceof AfterSaveEvent) {
      onAfterSave(event.getSource());
    } else if (event instanceof BeforeLinkSaveEvent) {
      onBeforeLinkSave(event.getSource(), ((BeforeLinkSaveEvent) event).getLinked());
    } else if (event instanceof AfterLinkSaveEvent) {
      onAfterLinkSave(event.getSource(), ((AfterLinkSaveEvent) event).getLinked());
    } else if (event instanceof BeforeDeleteEvent) {
      onBeforeDelete(event.getSource());
    } else if (event instanceof AfterDeleteEvent) {
      onAfterDelete(event.getSource());
    }
  }

  protected void onBeforeSave(Object entity) {}

  protected void onAfterSave(Object entity) {}

  protected void onBeforeLinkSave(Object parent, Object linked) {}

  protected void onAfterLinkSave(Object parent, Object linked) {}

  protected void onBeforeDelete(Object entity) {}

  protected void onAfterDelete(Object entity) {}

}