package org.jboss.errai.security.demo.client.local;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.jboss.errai.bus.client.api.BusErrorCallback;
import org.jboss.errai.bus.client.api.messaging.Message;
import org.jboss.errai.common.client.api.RemoteCallback;
import org.jboss.errai.ioc.client.api.AfterInitialization;
import org.jboss.errai.security.client.local.identity.Identity;
import org.jboss.errai.security.shared.api.identity.User;
import org.jboss.errai.security.shared.event.LoggedInEvent;
import org.jboss.errai.security.shared.event.LoggedOutEvent;
import org.jboss.errai.ui.nav.client.local.DefaultPage;
import org.jboss.errai.ui.nav.client.local.Page;
import org.jboss.errai.ui.nav.client.local.TransitionTo;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.EventHandler;
import org.jboss.errai.ui.shared.api.annotations.Templated;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;

@ApplicationScoped
@Templated("#root")
@Page(role = DefaultPage.class)
public class WelcomePage extends Composite {

  static final String ANONYMOUS = "anonymous";

  @Inject
  public
  @DataField
  Button startButton;

  @Inject
  @DataField
  private Label userLabel;

  @Inject
  private Identity identity;

  @Inject
  TransitionTo<Messages> startButtonClicked;

  @EventHandler("startButton")
  public void onStartButtonPress(ClickEvent e) {
    startButtonClicked.go();
  }

  @AfterInitialization
  private void setupUserLabel() {
    identity.getUser(new RemoteCallback<User>() {

      @Override
      public void callback(final User user) {
        userLabel.setText(user != null ? user.getFirstName() : ANONYMOUS);
      }
    }, new BusErrorCallback() {
      
      @Override
      public boolean error(Message message, Throwable throwable) {
        userLabel.setText(ANONYMOUS);
        return true;
      }
    });
  }

  @SuppressWarnings("unused")
  private void onLoggedIn(@Observes LoggedInEvent loggedInEvent) {
    userLabel.setText(loggedInEvent.getUser().getFirstName());
  }

  @SuppressWarnings("unused")
  private void onLoggedOut(@Observes LoggedOutEvent loggedOutEvent) {
    userLabel.setText(ANONYMOUS);
  }
}
