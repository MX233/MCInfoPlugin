package tax.cute.mcinfoplugin;

import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.SingleMessage;
import net.mamoe.mirai.utils.ExternalResource;
import tax.cute.minecraftinfoapi.Skin;
import tax.cute.minecraftinfoapi.UUID;
import top.mrxiaom.miraiutils.CommandModel;
import top.mrxiaom.miraiutils.CommandSender;
import top.mrxiaom.miraiutils.CommandSenderGroup;

import java.io.IOException;

public class MCSkinFile extends CommandModel {
    public MCSkinFile() {
        super("mcSkinFile");
    }

    @Override
    public void onCommand(CommandSender sender, SingleMessage[] args) {
        if (args[0].contentToString().equalsIgnoreCase("/mcSkinFile")) return;
        if (sender instanceof CommandSenderGroup) {
            CommandSenderGroup senderGroup = (CommandSenderGroup) sender;
            Group group = senderGroup.getGroup();

            if (args[0].contentToString().equalsIgnoreCase("/mcInfo")) {
                group.sendMessage("�÷�:/mcinfo [����/UUID]");
                return;
            }

            if (args[0].contentToString().isEmpty()) {
                group.sendMessage("��������Ч�û���");
                return;
            }

            try {
                sendSkinFile(group, args[0].contentToString());
            } catch (Exception e) {
                group.sendMessage("��ȡʧ��,���Ժ�����");
            }
        }
    }

    private void sendSkinFile(Group group, String name) throws IOException {
        String id;
        if (name.replace("-", "").length() == 32) {
            id = name;
        } else {
            UUID uuid = UUID.getId(name);
            if (uuid == null) {
                group.sendMessage("�޷��ҵ�����û�");
                return;
            }
            id = uuid.getId();
        }
        if (id == null) {
            group.sendMessage("�޷��ҵ�����û�");
            return;
        }

        byte[] bytes = Skin.getSkin(id).getSkinBytes();
        Image image = group.uploadImage(ExternalResource.create(bytes));
        group.sendMessage(image);
    }
}