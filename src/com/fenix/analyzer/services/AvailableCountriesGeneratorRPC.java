package com.fenix.analyzer.services;

import java.util.Map;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@WebService
@SOAPBinding(style = Style.RPC)
@XmlAccessorType(XmlAccessType.NONE)
public interface AvailableCountriesGeneratorRPC {
    @WebMethod
    Map<String, Boolean> getInfoAboutAvailable (String link);
}
