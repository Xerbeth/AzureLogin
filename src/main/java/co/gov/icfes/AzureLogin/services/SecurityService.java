package co.gov.icfes.AzureLogin.services;

import co.gov.icfes.AzureLogin.DTO.ApiResponse;
import co.gov.icfes.AzureLogin.services.implement.ISecurity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import static co.gov.icfes.AzureLogin.Utils.Constants.Variables.*;

@Service
public class SecurityService implements ISecurity {

    private static final Logger LOG = LogManager.getLogger(SecurityService.class);

    @Override
    public ApiResponse<String> GetToken() throws Exception {
        ApiResponse<String> response = new ApiResponse<String>();
        try{
            response.setData("");
            response.setMessage(SUCCESS_MESSAGE);
            response.setStatus(SUCCESS);

        }catch (Exception ex){
            LOG.info(SERVICE_EXECUTION_ERROR + SecurityService.class.getName());
            LOG.error(ex);
            response.setException(ex.getMessage());
        }
        return response;
    }
}
