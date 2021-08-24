package tax.cute.mcinfoplugin;

import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.data.ForwardMessageBuilder;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.PlainText;
import net.mamoe.mirai.message.data.SingleMessage;
import net.mamoe.mirai.utils.ExternalResource;
import tax.cute.minecraftinfoapi.PlayerInfo;
import tax.cute.minecraftinfoapi.UUID;
import top.mrxiaom.miraiutils.CommandModel;
import top.mrxiaom.miraiutils.CommandSender;
import top.mrxiaom.miraiutils.CommandSenderGroup;

import java.io.IOException;

public class MCInfo extends CommandModel {
    public MCInfo() {
        super("mcinfo");
    }

    @Override
    public void onCommand(CommandSender sender, SingleMessage[] args) {
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
                sendMcInfo(group, args[0].contentToString());
            } catch (Exception e) {
                group.sendMessage("��ȡʧ��,���Ժ�����");
                e.printStackTrace();
            }
        }
    }

    private void sendMcInfo(Group group, String name) throws IOException {
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

        PlayerInfo info = PlayerInfo.getPlayerInfo(id);
        if (info == null) {
            group.sendMessage("��ѯʧ��");
            return;
        }
        String text =
                "==MC������ѯ==" +
                        "\n[ �û��� ] " + info.getNowName() +
                        "\n[ UUID ] " + id;

        byte[] avatar_bytes = Util.getWebBytes(ApiUrl.MC_HEAD_SKIN + id);
        if (avatar_bytes == null) {
            group.sendMessage("��ѯʧ��");
            return;
        }

        Image avatar = group.uploadImage(ExternalResource.create(avatar_bytes));
        group.sendMessage(avatar.plus(text));

        ForwardMessageBuilder builder = new ForwardMessageBuilder(group);
        builder.add(group.getBot().getId(), group.getBot().getNick(), new PlainText("������"));

        for (int i = 0; i < info.getData().size(); i++) {
            String time;
            if (info.getData().get(i).getTimestamp() == -1) {
                time = "��ʼ�û���";
            } else {
                time = info.getData().get(i).getTime();
            }
            builder.add(group.getBot().getId(), group.getBot().getNick(), new PlainText(
                    "����:" + info.getData().get(i).getName() +
                            "\n�޸�ʱ��:" + time
            ));
        }

        group.sendMessage(builder.build());
    }
}
