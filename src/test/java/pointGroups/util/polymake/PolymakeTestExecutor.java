package pointGroups.util.polymake;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import pointGroups.util.PointGroupsUtility;
import pointGroups.util.Transformer;
import pointGroups.util.polymake.wrapper.PolymakeWrapper;


public class PolymakeTestExecutor
{

  /**
   * Execute a {@link Transformer} on the {@link PolymakeWrapper} to get the
   * result of a real polymake execution. <br><br>We first try to load the
   * {@linkplain settings.ini} to get the execution path of polymake. <br> After
   * that we make a one-shot request for the execution of the
   * {@link Transformer} (the polymake daemon will be shutdown immediately after
   * the execution). And then return the result of the transformer.
   * 
   * @param transformer
   * @return the constructed object from the {@link Transformer}
   * @throws IOException
   * @throws InterruptedException
   * @throws ExecutionException
   */
  public static <E> E execute(Transformer<E> transformer)
    throws IOException, InterruptedException, ExecutionException {

    String polyCmd = PointGroupsUtility.getPolymakePath().toString();
    String polyDriver = PointGroupsUtility.getPolymakeDriverPath().toString();

    final PolymakeWrapper pmWrapper = new PolymakeWrapper(polyCmd, polyDriver);
    pmWrapper.start();
    pmWrapper.sendRequest(transformer);
    E result = transformer.get();
    pmWrapper.stop();

    return result;
  }
}
