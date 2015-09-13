package net.stickycode.bootstrap;

import java.util.Collection;
import java.util.Iterator;
import java.util.ServiceLoader;

public interface StickyBootstrap {

  static StickyBootstrap crank(Object target, String... packages) {
    StickyBootstrap crank = crank();
    if (packages != null && packages.length > 0)
      return crank.scan(packages).inject(target);

    return crank.inject(target);
  }

  static StickyBootstrap crank(Object target, Class<?> base) {
    return crank().scan("net.stickycode", base.getPackage().getName()).inject(target);
  }

  static StickyBootstrap crank() {
    ServiceLoader<StickyBootstrap> loader = ServiceLoader.load(StickyBootstrap.class);
    Iterator<StickyBootstrap> bootstraps = loader.iterator();
    if (!bootstraps.hasNext())
      throw new RuntimeException("no net.stickycode.bootstrap.StickyBootstrap");

    StickyBootstrap bootstrap = bootstraps.next();
    if (bootstraps.hasNext())
      throw new RuntimeException("too many bootstraps");

    return bootstrap;
  }

  /**
   * Scan the given packages for components
   *
   * @return this bootstrap builder style
   */
  StickyBootstrap scan(String... packages);

  /**
   * Resolve the dependencies of the given object
   *
   * @return this bootstrap builder style
   */
  StickyBootstrap inject(Object value);

  /**
   * Find an instance of the give type, it MAY result in a new instance depending on its scoping.
   */
  <T> T find(Class<T> type)
      throws BeanNotFoundFailure;

  /**
   * @return true if a component of this type can be found
   */
  boolean canFind(Class<?> type);

  /**
   * useful for framework integrations where you need access to the Spring
   * context or Guice Injector
   *
   * @return the underlying implementation of the bootstrap,
   */
  Object getImplementation();

  void registerSingleton(String beanName, Object bean, Class<?> type);

  void registerType(String beanName, Class<?> type);

  void shutdown();

  StickyBootstrap scan(Collection<String> packageFilters);

  /**
   * Useful to extend the scanning, could be a Guice module or a Spring Resource to scan for example
   */
  void extend(Object extension);

  void start();
}
