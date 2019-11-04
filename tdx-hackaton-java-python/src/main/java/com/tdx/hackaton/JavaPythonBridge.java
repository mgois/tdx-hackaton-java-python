package com.tdx.hackaton;

import de.invesdwin.context.integration.script.IScriptTaskEngine;
import de.invesdwin.context.integration.script.IScriptTaskInputs;
import de.invesdwin.context.integration.script.IScriptTaskResults;
import de.invesdwin.context.python.runtime.contract.AScriptTaskPython;
import org.springframework.core.io.ClassPathResource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;

@Path("/hackaton")
public class JavaPythonBridge {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response hello() {
        final int[] result = new PythonMessageInvoker().run();

        return Response
            .ok()
            .entity(Arrays.toString(result))
            .build();
    }

    static class PythonMessageInvoker extends AScriptTaskPython<int[]> {
        @Override public void populateInputs(final IScriptTaskInputs inputs) {
            inputs.putIntegerVector("numbers", new int[] {1, 2, 3, 4});
        }

        @Override public void executeScript(final IScriptTaskEngine engine) {
            engine.eval(new ClassPathResource("scripts/multiplier.py", getClass().getClassLoader()));
        }

        @Override public int[] extractResults(final IScriptTaskResults results) {
            return results.getIntegerVector("result");
        }
    }
}