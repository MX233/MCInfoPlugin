package tax.cute.mcinfoplugin;

import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.data.ForwardMessageBuilder;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.PlainText;
import net.mamoe.mirai.message.data.SingleMessage;
import net.mamoe.mirai.utils.ExternalResource;
import tax.cute.minecraftinfoapi.CommonException;
import tax.cute.minecraftinfoapi.NameHistory;
import tax.cute.minecraftinfoapi.Player;
import tax.cute.minecraftinfoapi.utils.Http;
import top.mrxiaom.miraiutils.CommandModel;
import top.mrxiaom.miraiutils.CommandSender;
import top.mrxiaom.miraiutils.CommandSenderGroup;

import java.io.IOException;

public class MCInfo extends CommandModel {
    Plugin plugin;

    public MCInfo(Plugin plugin) {
        super("mcinfo");
        this.plugin = plugin;
    }

    @Override
    public void onCommand(CommandSender sender, SingleMessage[] args) {
        if (sender instanceof CommandSenderGroup) {
            CommandSenderGroup senderGroup = (CommandSenderGroup) sender;
            Group group = senderGroup.getGroup();

            if (args[0].contentToString().isEmpty()) {
                group.sendMessage("��������Ч�û���");
                return;
            }

            if (args[0].contentToString().equalsIgnoreCase("/mcInfo")) {
                group.sendMessage("�÷�:/mcInfo [�û���/uuid]");
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

    private void send(Group group, String name) throws IOException, CommonException {
        String uuid;
        if (Util.isUuid(name))
            uuid = name;
        else
            uuid = Player.getPlayer(name).getUuid();

        NameHistory nameHistory = NameHistory.getNameHistory(uuid);

        Image image = group.uploadImage(ExternalResource.create(Http.getHttp(ApiUrl.MC_HEAD_SKIN + uuid).getBytes()));

        group.sendMessage(
                image.plus(
                        "---MC��ҵ���---" +
                                "\n����: " + nameHistory.getCurrentName() +
                                "\nuuid: " + uuid
                )
        );

        ForwardMessageBuilder builder = new ForwardMessageBuilder(group);
        builder.add(group.getBot().getId(), group.getBot().getNick(), new PlainText("---MC����������б�---"));
        nameHistory.getNameHistoryData().forEach((data -> builder.add(group.getBot().getId(), group.getBot().getNick(), new PlainText(
                "����: " + data.getName() +
                        "\n�޸�ʱ��: " + tax.cute.minecraftinfoapi.utils.Util.toTime(data.getChangedToAt())
        ))));
        group.sendMessage(builder.build());
    }
}