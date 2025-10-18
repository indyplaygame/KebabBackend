package api.indy.kebab.validation;

/**
 * Interface defining validation groups for Jakarta Bean Validation.
 *
 * <p>Validation groups are used to apply specific validation rules
 * to different contexts or operations. This interface contains
 * nested interfaces representing these groups.</p>
 */
@SuppressWarnings("NewClassNamingConvention")
public interface ValidationGroups {

    /**
     * Validation group for operations related to creating new entities.
     */
    interface OnCreate {}
}
