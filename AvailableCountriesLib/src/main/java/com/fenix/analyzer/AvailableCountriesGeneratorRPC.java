package com.fenix.analyzer;

import java.util.Map;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

@WebService
@SOAPBinding(style = Style.RPC)
public interface AvailableCountriesGeneratorRPC {
    @WebMethod
    Map<String, Boolean> getInfoAboutAvailable (String link);
}
