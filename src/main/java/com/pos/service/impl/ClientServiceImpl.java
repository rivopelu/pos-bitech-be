package com.pos.service.impl;

import com.pos.entities.Client;
import com.pos.entities.ClientContact;
import com.pos.entities.repositories.AccountRepository;
import com.pos.entities.repositories.ClientContactRepository;
import com.pos.entities.repositories.ClientRepository;
import com.pos.enums.ResponseEnum;
import com.pos.exception.BadRequestException;
import com.pos.exception.SystemErrorException;
import com.pos.request.RequestCreateClient;
import com.pos.service.AccountService;
import com.pos.service.ClientService;
import com.pos.utils.EntityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final ClientContactRepository clientContactRepository;
    private final AccountRepository accountRepository;
    private final AccountService accountService;

    @Override
    public ResponseEnum createClient(RequestCreateClient req) {
        boolean checkExistClient = clientRepository.existsClientByCompanyNameAndActiveTrue(req.getCompanyName());
        boolean checkPic = accountRepository.existsAccountByUsernameAndActiveTrue(req.getPicId());
        if (checkPic){
            throw new BadRequestException(ResponseEnum.PIC_NOT_FOUND);
        }
        if (checkExistClient) {
            throw new BadRequestException(ResponseEnum.CLIENT_ALREADY_EXIST);
        }
        try {
            Client buildClient = Client.builder()
                    .companyName(req.getCompanyName())
                    .companyLogo(req.getCompanyLogo())
                    .address(req.getAddress())
                    .build();
            EntityUtils.created(buildClient, accountService.getCurrentAccountId());
            Client client = clientRepository.save(buildClient);

            List<RequestCreateClient.Contact> contacts = req.getContacts();
            List<ClientContact> clientContacts = new ArrayList<>();
            for (RequestCreateClient.Contact contact : contacts) {
                ClientContact buildContact = ClientContact.builder()
                        .contact(contact.getContact())
                        .description(contact.getDescription())
                        .type(contact.getType())
                        .client(client)
                        .build();
                clientContacts.add(buildContact);
            }
            clientContactRepository.saveAll(clientContacts);
            return ResponseEnum.SUCCESS;
        } catch (Exception e) {
            throw new SystemErrorException(e);
        }
    }
}
