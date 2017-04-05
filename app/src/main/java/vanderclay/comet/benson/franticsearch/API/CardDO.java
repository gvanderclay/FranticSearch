package vanderclay.comet.benson.franticsearch.API;

import java.io.Serializable;

/**
 * Created by ben on 3/29/17.
 */

public class CardDO implements Serializable {


    private Integer id;
    private String name;
    private Integer manaCost;
    private Integer cmc;
    private String colors;
    private String type;
    private Integer power;
    private Integer toughness;
    private Integer loyalty;
    private String imageName;
    private Boolean reserved;
    private String releaseDate;
    private String starter;
    private Boolean owned;

    public CardDO(){ }



    public CardDO(Integer id,
                  String name,
                  Integer manaCost,
                  Integer cmc,
                  String colors,
                  String type,
                  Integer power,
                  Integer toughness,
                  Integer loyalty,
                  String imageName,
                  Boolean reserved,
                  String releaseDate,
                  String starter,
                  Boolean owned) {

        this.id = id;
        this.name = name;
        this.manaCost = manaCost;
        this.cmc = cmc;
        this.colors = colors;
        this.type = type;
        this.power = power;
        this.toughness = toughness;
        this.loyalty = loyalty;
        this.imageName = imageName;
        this.reserved = reserved;
        this.releaseDate = releaseDate;
        this.starter = starter;
        this.owned = owned;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getManaCost() {
        return manaCost;
    }

    public void setManaCost(Integer manaCost) {
        this.manaCost = manaCost;
    }

    public Integer getCmc() {
        return cmc;
    }

    public void setCmc(Integer cmc) {
        this.cmc = cmc;
    }

    public String getColors() {
        return colors;
    }

    public void setColors(String colors) {
        this.colors = colors;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getPower() {
        return power;
    }

    public void setPower(Integer power) {
        this.power = power;
    }

    public Integer getToughness() {
        return toughness;
    }

    public void setToughness(Integer toughness) {
        this.toughness = toughness;
    }

    public Integer getLoyalty() {
        return loyalty;
    }

    public void setLoyalty(Integer loyalty) {
        this.loyalty = loyalty;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public Boolean getReserved() {
        return reserved;
    }

    public void setReserved(Boolean reserved) {
        this.reserved = reserved;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getStarter() {
        return starter;
    }

    public void setStarter(String starter) {
        this.starter = starter;
    }

    public Boolean getOwned() {
        return owned;
    }

    public void setOwned(Boolean owned) {
        this.owned = owned;
    }

    @Override
    public String toString() {
        return "CardDO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", manaCost=" + manaCost +
                ", cmc=" + cmc +
                ", colors='" + colors + '\'' +
                ", type='" + type + '\'' +
                ", power=" + power +
                ", toughness=" + toughness +
                ", loyalty=" + loyalty +
                ", imageName='" + imageName + '\'' +
                ", reserved=" + reserved +
                ", releaseDate='" + releaseDate + '\'' +
                ", starter='" + starter + '\'' +
                ", owned=" + owned +
                '}';
    }
}
