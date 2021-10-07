package tax.cute.mcinfoplugin;

import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.SingleMessage;
import net.mamoe.mirai.utils.ExternalResource;
import tax.cute.minecraftinfoapi.CommonException;
import tax.cute.minecraftinfoapi.Player;
import tax.cute.minecraftinfoapi.utils.Http;
import top.mrxiaom.miraiutils.CommandModel;
import top.mrxiaom.miraiutils.CommandSender;
import top.mrxiaom.miraiutils.CommandSenderGroup;

import java.io.IOException;

public class MCSkin extends CommandModel {
    Plugin plugin;
    public MCSkin(Plugin plugin) {
        super("McSkin");
        this.plugin = plugin;
    }

    @Override
    public void onCommand(CommandSender sender, SingleMessage[] args) {
        if (sender instanceof CommandSenderGroup) {
            CommandSenderGroup senderGroup = (CommandSenderGroup)sender;
            Group group = senderGroup.getGroup();

            if (args[0].contentToString().isEmpty()) {
                group.sendMessage("��������Ч���û���");
                return;
            }

            if (args[0].contentToString().equalsIgnoreCase("/mcskin")) {
                group.sendMessage("�÷�:/mcskin [�û���/uuid]");
                return;
            }

            try {
                send(group, args[0].contentToString());
            } catch (IOException e) {
                plugin.getLogger().warning(e.getMessage());
            } catch (CommonException e) {
                group.sendMessage("��ѯʧ��:�޷����ҵ�������");
            }
        }
    }

    private void send(Group group,String name) throws IOException, CommonException {
        String uuid;
        if (Util.isUuid(name))
            uuid = name;
        else
            uuid = Player.getPlayer(name).getUuid();

        Image image = group.uploadImage(ExternalResource.create(Http.getHttp(ApiUrl.MC_BODY_SKIN + uuid).getBytes()));

        group.sendMessage(image);
    }
}
