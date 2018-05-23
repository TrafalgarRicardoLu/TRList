package utils;

import net.fortuna.ical4j.model.property.Uid;

import java.util.UUID;

/**
 * @author trafalgar
 *
 * get randomUUID use for event uid
 */
public class UidGenerator implements net.fortuna.ical4j.util.UidGenerator {
    @Override
    public Uid generateUid() {
        return new Uid(UUID.randomUUID().toString());
    }
}
