package be.technobel.makerhub_backend.bll.services.implementations;

import be.technobel.makerhub_backend.bll.services.PricingService;
import be.technobel.makerhub_backend.dal.models.enums.LicenseType;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PricingServiceImpl implements PricingService {

    private static final Map<LicenseType, Double> LICENSE_PRICING = Map.of(
            LicenseType.BASIC, 24.95,
            LicenseType.PREMIUM, 49.95,
            LicenseType.TRACKOUT, 99.95,
            LicenseType.UNLIMITED, 199.95,
            LicenseType.EXCLUSIVE, 1000.00
    );

    @Override
    public double getPriceForLicense(LicenseType licenseType) {
        return LICENSE_PRICING.getOrDefault(licenseType, 0.0);
    }
}
