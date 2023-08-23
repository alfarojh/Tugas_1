import java.util.ArrayList;
import java.util.Arrays;

public class MemberController {
    private final ArrayList<Member> members = new ArrayList<>();

    /**
     * Mendapatkan indeks baru untuk anggota dengan cara menentukan indeks
     * berikutnya berdasarkan ID anggota terakhir dalam daftar.
     *
     * @return Indeks baru untuk anggota.
     */
    private int getNewIndexMember() {
        if (members.size() == 0) return 1; // Jika daftar kosong, indeks pertama adalah 1.
        return members.get(members.size() - 1).getId() + 1;
    }

    /**
     * Mendapatkan ID anggota berdasarkan alamat yang cocok.
     *
     * @param address Alamat yang akan dicocokkan.
     * @return ID anggota yang sesuai dengan alamat, atau -1 jika tidak ditemukan.
     */
    public int getIDByAddress(String address) {
        for (Member member : members) {
            if (member.getAddress().equalsIgnoreCase(address)) {
                return member.getId();
            }
        }
        return -1; // Mengembalikan -1 jika tidak ditemukan.
    }

    /**
     * Mendapatkan ID anggota berdasarkan nomor telepon yang cocok.
     *
     * @param phoneNumber Nomor telepon yang akan dicocokkan.
     * @return ID anggota yang sesuai dengan nomor telepon, atau -1 jika tidak ditemukan.
     */
    public int getIDByPhoneNumber(String phoneNumber) {
        for (Member member : members) {
            if (member.getPhoneNumber().equalsIgnoreCase(phoneNumber)) {
                return member.getId();
            }
        }
        return -1; // Mengembalikan -1 jika tidak ditemukan.
    }

    /**
     * Mendapatkan ID anggota berdasarkan nama yang cocok.
     *
     * @param name Nama yang akan dicocokkan.
     * @return ID anggota yang sesuai dengan nama, atau -1 jika tidak ditemukan.
     */
    public int getIDByName(String name) {
        for (Member member : members) {
            if (member.getName().equalsIgnoreCase(name)) {
                return member.getId();
            }
        }
        return -1; // Mengembalikan -1 jika tidak ditemukan.
    }

    /**
     * Memeriksa apakah anggota dengan ID tertentu ada dalam daftar anggota.
     *
     * @param ID ID anggota yang akan diperiksa.
     * @return True jika anggota dengan ID tersebut ada, false jika tidak.
     */
    public boolean isMemberExistByID(int ID) {
        for (Member member : members) {
            if (member.getId() == ID) {
                return true;
            }
        }
        return false;
    }

    /**
     * Menambahkan anggota baru ke dalam daftar anggota.
     *
     * @param name Nama anggota baru.
     * @param address Alamat anggota baru.
     * @param phoneNumber Nomor telepon anggota baru.
     * @return True jika penambahan anggota berhasil, false jika gagal.
     */
    public boolean addMember(String name, String address, String phoneNumber) {
        members.add(new Member(getNewIndexMember(), name, address, phoneNumber));
        return true; // Selalu mengembalikan true karena asumsi penambahan selalu berhasil.
    }

    /**
     * Memperbarui nama anggota berdasarkan ID anggota.
     *
     * @param id ID anggota yang akan diperbarui namanya.
     * @param name Nama baru yang akan diterapkan.
     * @return True jika pembaruan nama berhasil, false jika anggota dengan ID tersebut tidak ditemukan.
     */
    public boolean updateMemberName(int id, String name) {
        for (Member member : members) {
            if (member.getId() == id) {
                member.setName(name);
                return true;
            }
        }
        return false; // Mengembalikan false jika anggota dengan ID tersebut tidak ditemukan.
    }

    /**
     * Memperbarui alamat anggota berdasarkan ID anggota.
     *
     * @param id ID anggota yang akan diperbarui alamatnya.
     * @param address Alamat baru yang akan diterapkan.
     * @return True jika pembaruan alamat berhasil, false jika anggota dengan ID tersebut tidak ditemukan.
     */
    public boolean updateMemberAddress(int id, String address) {
        for (Member member : members) {
            if (member.getId() == id) {
                member.setAddress(address);
                return true;
            }
        }
        return false; // Mengembalikan false jika anggota dengan ID tersebut tidak ditemukan.
    }

    /**
     * Memperbarui nomor telepon anggota berdasarkan ID anggota.
     *
     * @param id ID anggota yang akan diperbarui nomor teleponnya.
     * @param phoneNumber Nomor telepon baru yang akan diterapkan.
     * @return True jika pembaruan nomor telepon berhasil, false jika anggota dengan ID tersebut tidak ditemukan.
     */
    public boolean updateMemberPhone(int id, String phoneNumber) {
        for (Member member : members) {
            if (member.getId() == id) {
                member.setPhoneNumber(phoneNumber);
                return true;
            }
        }
        return false; // Mengembalikan false jika anggota dengan ID tersebut tidak ditemukan.
    }

    /**
     * Menonaktifkan anggota berdasarkan ID anggota.
     *
     * @param id ID anggota yang akan dinonaktifkan.
     * @return True jika penghapusan anggota berhasil, false jika anggota dengan ID tersebut tidak ditemukan.
     */
    public boolean removeMember(int id) {
        for (Member member : members) {
            if (member.getId() == id) {
                member.setStatusActive(false);
                return true;
            }
        }
        return false; // Mengembalikan false jika anggota dengan ID tersebut tidak ditemukan.
    }

    /**
     * Mendapatkan nama anggota berdasarkan ID anggota.
     *
     * @param id ID anggota yang namanya akan diambil.
     * @return Nama anggota yang sesuai dengan ID, atau null jika tidak ditemukan.
     */
    public String getNameByID(int id) {
        for (Member member : members) {
            if (member.getId() == id) {
                return member.getName();
            }
        }
        return null; // Mengembalikan null jika anggota dengan ID tersebut tidak ditemukan.
    }

    /**
     * Mendapatkan daftar anggota aktif dalam bentuk array 2D.
     *
     * @return Array 2D yang berisi daftar anggota aktif.
     */
    public String[][] getListMembers() {
        String[][] listMembers = new String[0][4];
        for (Member member : members) {
            if (member.isStatusActive()) {
                String[][] temp = new String[listMembers.length + 1][4];

                System.arraycopy(listMembers, 0, temp, 0, listMembers.length);
                temp[listMembers.length][0] = String.valueOf(member.getId());
                temp[listMembers.length][1] = member.getName();
                temp[listMembers.length][2] = member.getAddress();
                temp[listMembers.length][3] = member.getPhoneNumber();
                listMembers = temp;
            }
        }
        return listMembers;
    }

}
