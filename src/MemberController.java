import java.util.ArrayList;

public class MemberController {
    private ArrayList<Member> members = new ArrayList<>();

    private int getNewIndexMember() {
        if (members.size() == 0) return 1;
        return members.get(members.size() - 1).getId() + 1;
    }

    public int getIDByAddress(String address) {
        for (Member member : members) {
            if (member.getAddress().equalsIgnoreCase(address)) {
                return member.getId();
            }
        }
        return -1;
    }

    public int getIDByPhoneNumber(String phoneNumber) {
        for (Member member : members) {
            if (member.getPhoneNumber().equalsIgnoreCase(phoneNumber)) {
                return member.getId();
            }
        }
        return -1;
    }

    public int getIDByName(String name) {
        for (Member member : members) {
            if (member.getName().equalsIgnoreCase(name)) {
                return member.getId();
            }
        }
        return -1;
    }

    public boolean addMember(String name, String address, String phoneNumber) {
        members.add(new Member(getNewIndexMember(), name, address, phoneNumber));
        return true;
    }

    public boolean removeMember(int id) {
        for (Member member : members) {
            if (member.getId() == id) {
                member.setStatusActive(false);
                return true;
            }
        }
        return false;
    }

    public String getListMember() {
        String text = "";
        for (Member member : members) {
            if (member.isStatusActive()) {
                text += member.getId() + ". "
                        + member.getName() + " - "
                        + member.getAddress() + " - "
                        + member.getPhoneNumber() + "\n";
            }
        }
        if (text.equals("")) return "Peserta Tidak Ditemukan.";
        return text;
    }
}
