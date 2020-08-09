package com.svetikov.ecommerceshop.dto.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;

@Data
public class AbstractDto implements Serializable {

    Long id;

    public interface Create {
    }

    public interface Update {
    }

    public interface Existing {
    }

    public interface StandardView {
    }

    public interface DetailsView {

    }
}
