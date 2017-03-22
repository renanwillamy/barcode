package com.sample.models.warehouse;


public class Warehouse {

    private long id;
    private String code;
    private String description;
    private boolean active;
    private boolean third;
    private boolean production;
    private boolean netable;
    private boolean lpnControlled;

    public Warehouse(long id, String code, String description, boolean active, boolean third,
                     boolean production, boolean netable, boolean lpnControlled) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.active = active;
        this.third = third;
        this.production = production;
        this.netable = netable;
        this.lpnControlled = lpnControlled;
    }

    public Warehouse() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isThird() {
        return third;
    }

    public void setThird(boolean third) {
        this.third = third;
    }

    public boolean isProduction() {
        return production;
    }

    public void setProduction(boolean production) {
        this.production = production;
    }

    public boolean isNetable() {
        return netable;
    }

    public void setNetable(boolean netable) {
        this.netable = netable;
    }

    public boolean isLpnControlled() {
        return lpnControlled;
    }

    public void setLpnControlled(boolean lpnControlled) {
        this.lpnControlled = lpnControlled;
    }
}
