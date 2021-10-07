package tax.cute.mcinfoplugin;

import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.SingleMessage;
import net.mamoe.mirai.utils.ExternalResource;
import tax.cute.minecraftinfoapi.CommonException;
import tax.cute.minecraftinfoapi.Player;
import tax.cute.minecraftinfoapi.Profile;
import top.mrxiaom.miraiutils.CommandModel;
import top.mrxiaom.miraiutils.CommandSender;
import top.mrxiaom.miraiutils.CommandSenderGroup;

import java.io.IOException;

public class MCSkinFile extends CommandModel {
    Plugin plugin;

    public MCSkinFile(Plugin plugin) {
        super("mcSkinFile");
        this.plugin = plugin;
    }

    @Override
    public void onCommand(CommandSender sender, SingleMessage[] args) {
        if (sender instanceof CommandSenderGroup) {
            CommandSenderGroup senderGroup = (CommandSenderGroup) sender;
            Group group = senderGroup.getGroup();

            if (args[0].contentToString().isEmpty()) {
                group.sendMessage("��������Ч���û���");
                return;
            }

            if (args[0].contentToString().equalsIgnoreCase("/mcSkinFile")) {
                group.sendMessage("�÷�:/mcskinfile [�û���/uuid]");
                return;
            }

            try {
                send(group, args[0].contentToString());
            } catch (IOException e) {
                group.sendMessage("����ʧ��,���Ժ�����");
            } catch (CommonException e) {
                group.sendMessage("��ѯʧ��:�޷����ҵ�������");
            } catch (Exception e) {
                group.sendMessage(String.valueOf(e));
                e.printStackTrace();
            }
        }
    }

    private void send(Group group, String name) throws IOException, CommonException {
        String uuid;
        if(Util.isUuid(name))
            uuid = name;
        else
            uuid = Player.getPlayer(name).getUuid();

        Profile profile = Profile.getProfile(uuid);

        if (profile.getSkinUrl() != null) {
            Image image = group.uploadImage(ExternalResource.create(profile.getSkinBytes()));
            group.sendMessage(image);
        } else {
            group.sendMessage("��ȡʧ��");
        }

        if (profile.getCapeUrl() != null) {
            Image image = group.uploadImage(ExternalResource.create(profile.getCapeBytes()));
            group.sendMessage(image);
        }
    }
}