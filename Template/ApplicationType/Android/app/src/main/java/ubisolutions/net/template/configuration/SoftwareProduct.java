package ubisolutions.net.template.configuration;

public class SoftwareProduct {

    public String ProductId;
    public String ProductVersion;
    public int InstallOrder;
    public String ProductPath;
    public String TargetConfPath;
    public boolean updateConfig;
    public boolean launchAfterUpdate;
    public boolean isSelfUpdate;

    public SoftwareProduct(String stringProduct){
        String[] infosProduct = stringProduct.split("\\|");
        this.ProductId =  infosProduct[0];
        this.ProductVersion = infosProduct[1];
        this.InstallOrder = Integer.parseInt(infosProduct[2]);
        this.ProductPath = infosProduct[3];
        this.updateConfig = infosProduct[4].equals("1");
        this.launchAfterUpdate = infosProduct[5].equals("1");
        this.TargetConfPath = infosProduct[6];
        this.isSelfUpdate = infosProduct[7].equals("1");
    }

}