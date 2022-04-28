package com.android.settingslib.datetime;

import com.android.i18n.timezone.CountryTimeZones;
import com.android.i18n.timezone.TimeZoneFinder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ZoneGetter$ZoneGetterData {
    public List<String> lookupTimeZoneIdsByCountry(String str) {
        CountryTimeZones lookupCountryTimeZones = TimeZoneFinder.getInstance().lookupCountryTimeZones(str);
        if (lookupCountryTimeZones == null) {
            return null;
        }
        List<CountryTimeZones.TimeZoneMapping> timeZoneMappings = lookupCountryTimeZones.getTimeZoneMappings();
        ArrayList arrayList = new ArrayList(timeZoneMappings.size());
        for (CountryTimeZones.TimeZoneMapping timeZoneId : timeZoneMappings) {
            arrayList.add(timeZoneId.getTimeZoneId());
        }
        return Collections.unmodifiableList(arrayList);
    }
}
