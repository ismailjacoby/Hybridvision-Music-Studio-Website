package be.technobel.makerhub_backend.bll.services;

import be.technobel.makerhub_backend.dal.models.enums.LicenseType;

public interface PricingService {
    double getPriceForLicense(LicenseType licenseType);
}
