package net.stickycode.bootstrap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.stickycode.stereotype.StickyComponent;

@StickyComponent
public class LoggingStickySystem
    implements StickySystem {


  private Logger log = LoggerFactory.getLogger(getClass());

  @Override
  public String getLabel() {
    return "StickySystemLogger";
  }

  @Override
  public Package getPackage() {
    return getClass().getPackage();
  }

  @Override
  public void start() {
    log.info("start");
  }

  @Override
  public void pause() {
    log.info("pause");
  }

  @Override
  public void unpause() {
    log.info("unpause");
  }

  @Override
  public void shutdown() {
    log.info("shutdown");
  }

  @Override
  public boolean uses(StickySystem system) {
    return false;
  }

  @Override
  public boolean isUsedBy(StickySystem system) {
    return true;
  }

}
